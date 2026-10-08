package com.tophantu.inventory.reservation.adapter.outbound.inventory;

import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;
import com.tophantu.inventory.reservation.application.command.port.outbound.ReserveInventoryStrategy;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Component
public class RedisReserveInventoryAdapter implements ReserveInventoryStrategy {

    private static final String RESERVATIONS_KEY_PREFIX = "reservations:";
    private static final String HOLD_KEY_PREFIX = "reservation-hold:";
    private static final DefaultRedisScript<Long> RESERVE_SCRIPT = new DefaultRedisScript<>("""
            local inventoryKey = KEYS[1]
            local reservationsKey = KEYS[2]
            local holdKeyPrefix = ARGV[1]
            local now = tonumber(ARGV[2])
            local requestedQuantity = tonumber(ARGV[3])
            local holdId = ARGV[4]
            local expiresAt = tonumber(ARGV[5])
            local productId = ARGV[6]

            local expiredHoldIds = redis.call('ZRANGEBYSCORE', reservationsKey, '-inf', now)
            for _, expiredHoldId in ipairs(expiredHoldIds) do
                local holdKey = holdKeyPrefix .. expiredHoldId
                local expiredQuantity = redis.call('HGET', holdKey, 'quantity')
                if expiredQuantity then
                    redis.call('INCRBY', inventoryKey, expiredQuantity)
                end
                redis.call('ZREM', reservationsKey, expiredHoldId)
                redis.call('DEL', holdKey)
            end

            local availableQuantity = redis.call('GET', inventoryKey)
            if not availableQuantity then
                return -1
            end
            if tonumber(availableQuantity) < requestedQuantity then
                return 0
            end

            redis.call('DECRBY', inventoryKey, requestedQuantity)
            redis.call('HSET', holdKeyPrefix .. holdId,
                'productId', productId,
                'quantity', requestedQuantity,
                'expiresAt', expiresAt)
            redis.call('ZADD', reservationsKey, expiresAt, holdId)
            return 1
            """, Long.class);

    private final StringRedisTemplate stringRedisTemplate;

    public RedisReserveInventoryAdapter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ReservationStrategy strategy() {
        return ReservationStrategy.REDIS;
    }

    @Override
    public void reserve(Long productId, long quantity, LocalDateTime expiresAt) {
        Long result = stringRedisTemplate.execute(
                RESERVE_SCRIPT,
                List.of(RedisInventoryPreloadAdapter.inventoryKey(productId), reservationsKey(productId)),
                HOLD_KEY_PREFIX,
                Long.toString(System.currentTimeMillis()),
                Long.toString(quantity),
                UUID.randomUUID().toString(),
                Long.toString(toEpochMillis(expiresAt)),
                productId.toString()
        );

        if (result == null || result == -1L) {
            throw new BusinessException(ReservationErrorCode.INVENTORY_NOT_FOUND);
        }
        if (result == 0L) {
            throw new BusinessException(ReservationErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY);
        }
    }

    private String reservationsKey(Long productId) {
        return RESERVATIONS_KEY_PREFIX + productId;
    }

    private long toEpochMillis(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}

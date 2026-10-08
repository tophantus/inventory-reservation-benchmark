package com.tophantu.inventory.reservation.adapter.outbound.inventory;

import com.tophantu.inventory.reservation.application.command.port.outbound.PreloadRedisInventoryPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisInventoryPreloadAdapter implements PreloadRedisInventoryPort {

    private static final String INVENTORY_KEY_PREFIX = "inventory:";

    private final StringRedisTemplate stringRedisTemplate;

    public RedisInventoryPreloadAdapter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void preload(Long productId, long availableQuantity) {
        stringRedisTemplate.opsForValue().set(inventoryKey(productId), Long.toString(availableQuantity));
    }

    static String inventoryKey(Long productId) {
        return INVENTORY_KEY_PREFIX + productId;
    }
}

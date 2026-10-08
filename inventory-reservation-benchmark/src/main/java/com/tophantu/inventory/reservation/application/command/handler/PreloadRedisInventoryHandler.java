package com.tophantu.inventory.reservation.application.command.handler;

import com.tophantu.inventory.inventory.application.query.dto.FindInventoryByProductIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.FindInventoryByProductIdUseCase;
import com.tophantu.inventory.reservation.application.command.dto.PreloadRedisInventoryCommand;
import com.tophantu.inventory.reservation.application.command.port.inbound.PreloadRedisInventoryUseCase;
import com.tophantu.inventory.reservation.application.command.port.outbound.PreloadRedisInventoryPort;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class PreloadRedisInventoryHandler implements PreloadRedisInventoryUseCase {

    private final FindInventoryByProductIdUseCase findInventoryByProductIdUseCase;
    private final PreloadRedisInventoryPort preloadRedisInventoryPort;

    public PreloadRedisInventoryHandler(
            FindInventoryByProductIdUseCase findInventoryByProductIdUseCase,
            PreloadRedisInventoryPort preloadRedisInventoryPort
    ) {
        this.findInventoryByProductIdUseCase = findInventoryByProductIdUseCase;
        this.preloadRedisInventoryPort = preloadRedisInventoryPort;
    }

    @Override
    public void preload(PreloadRedisInventoryCommand command) {
        InventoryQueryResult inventory = findInventoryByProductIdUseCase
                .findInventoryByProductId(new FindInventoryByProductIdQuery(command.productId()))
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.INVENTORY_NOT_FOUND));

        preloadRedisInventoryPort.preload(
                inventory.productId(),
                inventory.quantity() - inventory.reservedQuantity()
        );
    }
}

package com.tophantu.inventory.inventory.domain.model;

import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;

import java.time.LocalDateTime;

public record Inventory(
        Long id,
        Long productId,
        long quantity,
        long reservedQuantity,
        long availableQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Inventory {
        if (quantity < 0) {
            throw new BusinessException(InventoryErrorCode.NEGATIVE_QUANTITY);
        }
        if (reservedQuantity < 0) {
            throw new BusinessException(InventoryErrorCode.NEGATIVE_RESERVED_QUANTITY);
        }
        if (reservedQuantity > quantity) {
            throw new BusinessException(InventoryErrorCode.RESERVED_QUANTITY_EXCEEDS_QUANTITY);
        }
        if (availableQuantity < 0) {
            throw new BusinessException(InventoryErrorCode.NEGATIVE_AVAILABLE_QUANTITY);
        }
    }
}

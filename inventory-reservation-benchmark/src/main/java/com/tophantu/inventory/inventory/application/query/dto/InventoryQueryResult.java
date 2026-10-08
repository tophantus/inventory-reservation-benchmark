package com.tophantu.inventory.inventory.application.query.dto;

import java.time.LocalDateTime;

public record InventoryQueryResult(
        Long id,
        Long productId,
        long quantity,
        long reservedQuantity,
        long availableQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

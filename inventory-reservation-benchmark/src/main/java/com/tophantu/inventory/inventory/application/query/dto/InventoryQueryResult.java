package com.tophantu.inventory.inventory.application.query.dto;

import java.time.LocalDateTime;

public record InventoryQueryResult(
        Long id,
        Long productId,
        long quantity,
        long reservedQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

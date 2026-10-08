package com.tophantu.inventory.product.application.query.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductQueryResult(
        Long id,
        Long shopId,
        String name,
        String sku,
        BigDecimal price,
        long quantity,
        long reservedQuantity,
        long availableQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

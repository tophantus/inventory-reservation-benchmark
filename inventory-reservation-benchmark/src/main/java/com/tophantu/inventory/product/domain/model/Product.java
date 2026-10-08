package com.tophantu.inventory.product.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Product(
        Long id,
        Long shopId,
        String name,
        String sku,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

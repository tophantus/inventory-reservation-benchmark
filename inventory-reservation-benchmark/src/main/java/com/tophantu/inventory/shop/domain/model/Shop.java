package com.tophantu.inventory.shop.domain.model;

import java.time.LocalDateTime;

public record Shop(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

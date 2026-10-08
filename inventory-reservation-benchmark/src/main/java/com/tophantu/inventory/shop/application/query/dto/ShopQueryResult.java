package com.tophantu.inventory.shop.application.query.dto;

import java.time.LocalDateTime;

public record ShopQueryResult(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

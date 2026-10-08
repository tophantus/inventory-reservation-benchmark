package com.tophantu.inventory.customer.application.query.dto;

import java.time.LocalDateTime;

public record CustomerQueryResult(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

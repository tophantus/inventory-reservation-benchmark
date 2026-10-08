package com.tophantu.inventory.customer.domain.model;

import java.time.LocalDateTime;

public record Customer(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

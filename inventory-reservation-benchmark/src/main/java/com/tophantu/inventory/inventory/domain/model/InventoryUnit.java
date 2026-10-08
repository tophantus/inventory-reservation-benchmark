package com.tophantu.inventory.inventory.domain.model;

import java.time.LocalDateTime;

public record InventoryUnit(
        Long id,
        Long inventoryId,
        LocalDateTime createdAt
) {
}

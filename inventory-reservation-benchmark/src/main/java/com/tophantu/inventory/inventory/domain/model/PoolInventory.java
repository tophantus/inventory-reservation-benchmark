package com.tophantu.inventory.inventory.domain.model;

public record PoolInventory(
        Long id,
        Long productId,
        long availableQuantity
) {
}

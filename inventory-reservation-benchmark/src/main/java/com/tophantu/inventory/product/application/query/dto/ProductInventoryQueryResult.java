package com.tophantu.inventory.product.application.query.dto;

public record ProductInventoryQueryResult(
        long quantity,
        long reservedQuantity
) {
}

package com.tophantu.inventory.reservation.application.command.dto;

public record ReservationInventoryQueryResult(
        long quantity,
        long reservedQuantity
) {

    public long availableQuantity() {
        return quantity - reservedQuantity;
    }
}

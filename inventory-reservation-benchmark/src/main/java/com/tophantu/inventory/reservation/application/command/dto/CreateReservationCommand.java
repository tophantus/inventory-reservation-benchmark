package com.tophantu.inventory.reservation.application.command.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateReservationCommand(
        @NotNull @Positive Long customerId,
        @NotNull @Positive Long productId,
        @Positive long quantity,
        @NotNull ReservationStrategy strategy
) {
}

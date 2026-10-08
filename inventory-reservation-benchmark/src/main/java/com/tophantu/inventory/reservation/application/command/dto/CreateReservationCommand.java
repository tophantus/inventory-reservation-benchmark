package com.tophantu.inventory.reservation.application.command.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CreateReservationCommand(
        @NotNull @Positive Long customerId,
        @NotNull @Positive Long productId,
        @Positive long quantity,
        @NotNull @Future LocalDateTime expiresAt
) {
}

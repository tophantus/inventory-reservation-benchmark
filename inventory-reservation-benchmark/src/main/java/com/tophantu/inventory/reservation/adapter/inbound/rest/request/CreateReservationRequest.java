package com.tophantu.inventory.reservation.adapter.inbound.rest.request;

import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateReservationRequest(
        @NotNull @Positive Long customerId,
        @NotNull @Positive Long productId,
        @Positive long quantity,
        @NotNull ReservationStrategy strategy
) {
}

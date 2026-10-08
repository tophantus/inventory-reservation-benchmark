package com.tophantu.inventory.inventory.application.command.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateInventoryCommand(
        @NotNull @Positive Long productId,
        @NotNull @PositiveOrZero Long quantity,
        @NotNull @PositiveOrZero Long reservedQuantity
) {
}

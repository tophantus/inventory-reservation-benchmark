package com.tophantu.inventory.inventory.application.command.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddInventoryUnitsCommand(
        @NotNull @Positive Long inventoryId,
        @Positive long quantity
) {
}

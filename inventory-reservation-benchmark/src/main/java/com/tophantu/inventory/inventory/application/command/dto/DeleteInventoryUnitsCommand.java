package com.tophantu.inventory.inventory.application.command.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record DeleteInventoryUnitsCommand(
        @NotEmpty List<@NotNull @Positive Long> unitIds
) {
}

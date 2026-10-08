package com.tophantu.inventory.inventory.application.command.port.inbound;

import com.tophantu.inventory.inventory.application.command.dto.AddInventoryUnitsCommand;

public interface AddInventoryUnitsUseCase {

    void addInventoryUnits(AddInventoryUnitsCommand command);
}

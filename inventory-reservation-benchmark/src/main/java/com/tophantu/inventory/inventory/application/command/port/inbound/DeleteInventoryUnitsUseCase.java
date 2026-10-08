package com.tophantu.inventory.inventory.application.command.port.inbound;

import com.tophantu.inventory.inventory.application.command.dto.DeleteInventoryUnitsCommand;

public interface DeleteInventoryUnitsUseCase {

    void deleteInventoryUnits(DeleteInventoryUnitsCommand command);
}

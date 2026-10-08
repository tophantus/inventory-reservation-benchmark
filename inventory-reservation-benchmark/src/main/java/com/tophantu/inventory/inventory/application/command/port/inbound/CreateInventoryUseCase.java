package com.tophantu.inventory.inventory.application.command.port.inbound;

import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryCommand;
import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryResult;

public interface CreateInventoryUseCase {

    CreateInventoryResult createInventory(CreateInventoryCommand command);
}

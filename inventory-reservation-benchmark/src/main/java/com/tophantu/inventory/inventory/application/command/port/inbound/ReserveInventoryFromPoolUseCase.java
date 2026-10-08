package com.tophantu.inventory.inventory.application.command.port.inbound;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryFromPoolCommand;

public interface ReserveInventoryFromPoolUseCase {

    void reserveInventory(ReserveInventoryFromPoolCommand command);
}

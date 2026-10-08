package com.tophantu.inventory.inventory.application.command.port.inbound;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryCommand;

public interface ReserveInventoryWithPessimisticLockUseCase {

    void reserveInventory(ReserveInventoryCommand command);
}

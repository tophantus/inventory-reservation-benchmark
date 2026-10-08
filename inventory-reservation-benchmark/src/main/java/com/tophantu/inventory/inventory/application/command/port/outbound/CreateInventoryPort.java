package com.tophantu.inventory.inventory.application.command.port.outbound;

import com.tophantu.inventory.inventory.domain.model.Inventory;

public interface CreateInventoryPort {

    Inventory save(Inventory inventory);
}

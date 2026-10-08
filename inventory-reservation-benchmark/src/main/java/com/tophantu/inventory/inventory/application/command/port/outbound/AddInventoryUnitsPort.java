package com.tophantu.inventory.inventory.application.command.port.outbound;

import com.tophantu.inventory.inventory.domain.model.InventoryUnit;

import java.util.List;

public interface AddInventoryUnitsPort {

    void saveAll(List<InventoryUnit> inventoryUnits);
}

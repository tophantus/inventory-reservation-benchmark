package com.tophantu.inventory.inventory.application.command.port.outbound;

import java.util.List;

public interface InventoryUnitPoolPort {

    List<Long> findUnitIdsForAllocation(Long inventoryId, long quantity);

    void deleteByIds(List<Long> unitIds);

    long countByInventoryId(Long inventoryId);

    void addUnits(Long inventoryId, long quantity);
}

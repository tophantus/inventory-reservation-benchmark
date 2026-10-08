package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.domain.model.InventoryUnit;

public final class InventoryUnitJpaMapper {

    private InventoryUnitJpaMapper() {
    }

    public static InventoryUnitJpaEntity toEntity(InventoryUnit inventoryUnit) {
        return new InventoryUnitJpaEntity(
                inventoryUnit.id(),
                inventoryUnit.inventoryId(),
                inventoryUnit.createdAt()
        );
    }
}

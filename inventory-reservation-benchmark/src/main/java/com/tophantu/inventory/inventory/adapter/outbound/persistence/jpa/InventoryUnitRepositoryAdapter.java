package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.application.command.port.outbound.AddInventoryUnitsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.DeleteInventoryUnitsPort;
import com.tophantu.inventory.inventory.domain.model.InventoryUnit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryUnitRepositoryAdapter implements AddInventoryUnitsPort, DeleteInventoryUnitsPort {

    private final InventoryUnitJpaRepository inventoryUnitJpaRepository;

    public InventoryUnitRepositoryAdapter(InventoryUnitJpaRepository inventoryUnitJpaRepository) {
        this.inventoryUnitJpaRepository = inventoryUnitJpaRepository;
    }

    @Override
    public void saveAll(List<InventoryUnit> inventoryUnits) {
        inventoryUnitJpaRepository.saveAll(inventoryUnits.stream()
                .map(InventoryUnitJpaMapper::toEntity)
                .toList());
    }

    @Override
    public void deleteByIds(List<Long> unitIds) {
        inventoryUnitJpaRepository.deleteByIdIn(unitIds);
    }
}

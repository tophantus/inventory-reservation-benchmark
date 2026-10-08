package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.application.command.port.outbound.AddInventoryUnitsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.DeleteInventoryUnitsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.InventoryUnitPoolPort;
import com.tophantu.inventory.inventory.domain.model.InventoryUnit;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class InventoryUnitRepositoryAdapter implements AddInventoryUnitsPort, DeleteInventoryUnitsPort, InventoryUnitPoolPort {

    private final InventoryUnitJpaRepository inventoryUnitJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    public InventoryUnitRepositoryAdapter(
            InventoryUnitJpaRepository inventoryUnitJpaRepository,
            JdbcTemplate jdbcTemplate
    ) {
        this.inventoryUnitJpaRepository = inventoryUnitJpaRepository;
        this.jdbcTemplate = jdbcTemplate;
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

    @Override
    public List<Long> findUnitIdsForAllocation(Long inventoryId, long quantity) {
        return inventoryUnitJpaRepository.findIdsForUpdateSkipLocked(inventoryId, quantity);
    }

    @Override
    public long countByInventoryId(Long inventoryId) {
        return inventoryUnitJpaRepository.countByInventoryId(inventoryId);
    }

    @Override
    public void addUnits(Long inventoryId, long quantity) {
        List<Object[]> batchArguments = java.util.stream.LongStream.range(0, quantity)
                .mapToObj(ignored -> new Object[]{inventoryId})
                .toList();
        jdbcTemplate.batchUpdate("insert into inventory_unit (inventory_id, created_at) values (?, current_timestamp)", batchArguments);
    }
}

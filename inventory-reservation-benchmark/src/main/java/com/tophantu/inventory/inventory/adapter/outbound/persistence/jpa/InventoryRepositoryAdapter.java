package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.application.command.port.outbound.CreateInventoryPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.AcquireInventoryPoolLockPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ChangeAvailableQuantityPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.FindPoolInventoryPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ReserveInventoryWithPessimisticLockPort;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.inventory.domain.model.PoolInventory;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

@Repository
public class InventoryRepositoryAdapter implements
        CreateInventoryPort,
        ReserveInventoryWithPessimisticLockPort,
        FindInventoryByIdPort,
        AcquireInventoryPoolLockPort,
        ChangeAvailableQuantityPort,
        FindPoolInventoryPort {

    private final InventoryJpaRepository inventoryJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    public InventoryRepositoryAdapter(InventoryJpaRepository inventoryJpaRepository, JdbcTemplate jdbcTemplate) {
        this.inventoryJpaRepository = inventoryJpaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Inventory save(Inventory inventory) {
        return InventoryJpaMapper.toDomain(inventoryJpaRepository.save(InventoryJpaMapper.toEntity(inventory)));
    }

    @Override
    public Optional<Inventory> findById(Long inventoryId) {
        return inventoryJpaRepository.findById(inventoryId)
                .map(InventoryJpaMapper::toDomain);
    }

    @Override
    public Optional<Inventory> findByProductId(Long productId) {
        return inventoryJpaRepository.findByProductId(productId)
                .map(InventoryJpaMapper::toDomain);
    }

    @Override
    public Optional<PoolInventory> findPoolByProductId(Long productId) {
        return inventoryJpaRepository.findPoolInventoryByProductId(productId)
                .map(inventory -> new PoolInventory(
                        inventory.getId(), inventory.getProductId(), inventory.getAvailableQuantity()
                ));
    }

    @Override
    public void reserve(Long productId, long quantity) {
        InventoryJpaEntity inventory = inventoryJpaRepository.findByProductIdForUpdate(productId)
                .orElseThrow(() -> new BusinessException(InventoryErrorCode.INVENTORY_NOT_FOUND));
        long availableQuantity = inventory.getQuantity() - inventory.getReservedQuantity();
        if (availableQuantity < quantity) {
            throw new BusinessException(InventoryErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY);
        }
        inventory.increaseReservedQuantity(quantity);
    }

    @Override
    public void acquire(Long inventoryId) {
        jdbcTemplate.queryForObject(
                "select pg_advisory_xact_lock(?)",
                (resultSet, rowNum) -> null,
                inventoryId
        );
    }

    @Override
    public void changeAvailableQuantity(Long inventoryId, long delta) {
        inventoryJpaRepository.changeAvailableQuantity(inventoryId, delta);
    }
}

package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.application.command.port.outbound.CreateInventoryPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ReserveInventoryWithPessimisticLockPort;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InventoryRepositoryAdapter implements
        CreateInventoryPort,
        ReserveInventoryWithPessimisticLockPort,
        FindInventoryByIdPort {

    private final InventoryJpaRepository inventoryJpaRepository;

    public InventoryRepositoryAdapter(InventoryJpaRepository inventoryJpaRepository) {
        this.inventoryJpaRepository = inventoryJpaRepository;
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
    public void reserve(Long productId, long quantity) {
        InventoryJpaEntity inventory = inventoryJpaRepository.findByProductIdForUpdate(productId)
                .orElseThrow(() -> new BusinessException(InventoryErrorCode.INVENTORY_NOT_FOUND));
        long availableQuantity = inventory.getQuantity() - inventory.getReservedQuantity();
        if (availableQuantity < quantity) {
            throw new BusinessException(InventoryErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY);
        }
        inventory.increaseReservedQuantity(quantity);
    }
}

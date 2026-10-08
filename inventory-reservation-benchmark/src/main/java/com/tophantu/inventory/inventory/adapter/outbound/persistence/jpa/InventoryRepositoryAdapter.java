package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.application.command.port.outbound.CreateInventoryPort;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InventoryRepositoryAdapter implements CreateInventoryPort, FindInventoryByIdPort {

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
}

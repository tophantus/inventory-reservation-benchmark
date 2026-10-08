package com.tophantu.inventory.inventory.application.command.port.outbound;

import com.tophantu.inventory.inventory.domain.model.PoolInventory;

import java.util.Optional;

public interface FindPoolInventoryPort {

    Optional<PoolInventory> findPoolByProductId(Long productId);
}

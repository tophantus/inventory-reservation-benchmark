package com.tophantu.inventory.inventory.application.query.port.outbound;

import com.tophantu.inventory.inventory.domain.model.Inventory;

import java.util.Optional;

public interface FindInventoryByIdPort {

    Optional<Inventory> findById(Long inventoryId);
}

package com.tophantu.inventory.inventory.application.query.port.inbound;

import com.tophantu.inventory.inventory.application.query.dto.FindInventoryByProductIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;

import java.util.Optional;

public interface FindInventoryByProductIdUseCase {

    Optional<InventoryQueryResult> findInventoryByProductId(FindInventoryByProductIdQuery query);
}

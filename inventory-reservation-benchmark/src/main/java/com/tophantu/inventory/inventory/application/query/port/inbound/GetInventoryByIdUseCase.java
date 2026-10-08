package com.tophantu.inventory.inventory.application.query.port.inbound;

import com.tophantu.inventory.inventory.application.query.dto.GetInventoryByIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;

public interface GetInventoryByIdUseCase {

    InventoryQueryResult getInventoryById(GetInventoryByIdQuery query);
}

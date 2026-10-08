package com.tophantu.inventory.inventory.application.query.handler;

import com.tophantu.inventory.inventory.application.query.dto.FindInventoryByProductIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.FindInventoryByProductIdUseCase;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindInventoryByProductIdHandler implements FindInventoryByProductIdUseCase {

    private final FindInventoryByIdPort findInventoryByIdPort;

    public FindInventoryByProductIdHandler(FindInventoryByIdPort findInventoryByIdPort) {
        this.findInventoryByIdPort = findInventoryByIdPort;
    }

    @Override
    public Optional<InventoryQueryResult> findInventoryByProductId(FindInventoryByProductIdQuery query) {
        return findInventoryByIdPort.findByProductId(query.productId())
                .map(this::toQueryResult);
    }

    private InventoryQueryResult toQueryResult(Inventory inventory) {
        return new InventoryQueryResult(
                inventory.id(), inventory.productId(), inventory.quantity(), inventory.reservedQuantity(),
                inventory.availableQuantity(),
                inventory.createdAt(), inventory.updatedAt()
        );
    }
}

package com.tophantu.inventory.inventory.application.query.handler;

import com.tophantu.inventory.inventory.application.query.dto.GetInventoryByIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.GetInventoryByIdUseCase;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class GetInventoryByIdHandler implements GetInventoryByIdUseCase {

    private final FindInventoryByIdPort findInventoryByIdPort;

    public GetInventoryByIdHandler(FindInventoryByIdPort findInventoryByIdPort) {
        this.findInventoryByIdPort = findInventoryByIdPort;
    }

    @Override
    public InventoryQueryResult getInventoryById(GetInventoryByIdQuery query) {
        Inventory inventory = findInventoryByIdPort.findById(query.inventoryId())
                .orElseThrow(() -> new BusinessException(InventoryErrorCode.INVENTORY_NOT_FOUND));

        return new InventoryQueryResult(
                inventory.id(),
                inventory.productId(),
                inventory.quantity(),
                inventory.reservedQuantity(),
                inventory.availableQuantity(),
                inventory.createdAt(),
                inventory.updatedAt()
        );
    }
}

package com.tophantu.inventory.product.adapter.outbound.inventory;

import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.CreateInventoryUseCase;
import com.tophantu.inventory.inventory.application.query.dto.FindInventoryByProductIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.FindInventoryByProductIdUseCase;
import com.tophantu.inventory.product.application.command.port.outbound.CreateProductInventoryPort;
import com.tophantu.inventory.product.application.query.dto.ProductInventoryQueryResult;
import com.tophantu.inventory.product.application.query.port.outbound.FindProductInventoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InventoryApplicationAdapter implements CreateProductInventoryPort, FindProductInventoryPort {

    private final CreateInventoryUseCase createInventoryUseCase;
    private final FindInventoryByProductIdUseCase findInventoryByProductIdUseCase;

    public InventoryApplicationAdapter(
            CreateInventoryUseCase createInventoryUseCase,
            FindInventoryByProductIdUseCase findInventoryByProductIdUseCase
    ) {
        this.createInventoryUseCase = createInventoryUseCase;
        this.findInventoryByProductIdUseCase = findInventoryByProductIdUseCase;
    }

    @Override
    public void create(Long productId, long quantity) {
        createInventoryUseCase.createInventory(new CreateInventoryCommand(productId, quantity, 0L));
    }

    @Override
    public Optional<ProductInventoryQueryResult> findByProductId(Long productId) {
        return findInventoryByProductIdUseCase.findInventoryByProductId(new FindInventoryByProductIdQuery(productId))
                .map(this::toProductInventoryQueryResult);
    }

    private ProductInventoryQueryResult toProductInventoryQueryResult(InventoryQueryResult inventory) {
        return new ProductInventoryQueryResult(
                inventory.quantity(), inventory.reservedQuantity(), inventory.availableQuantity()
        );
    }
}

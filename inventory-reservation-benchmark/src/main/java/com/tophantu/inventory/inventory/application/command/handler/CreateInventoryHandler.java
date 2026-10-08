package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryCommand;
import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryResult;
import com.tophantu.inventory.inventory.application.command.port.inbound.CreateInventoryUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.CreateInventoryPort;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.product.application.query.dto.GetProductByIdQuery;
import com.tophantu.inventory.product.application.query.port.inbound.GetProductByIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateInventoryHandler implements CreateInventoryUseCase {

    private final CreateInventoryPort createInventoryPort;
    private final GetProductByIdUseCase getProductByIdUseCase;

    public CreateInventoryHandler(
            CreateInventoryPort createInventoryPort,
            GetProductByIdUseCase getProductByIdUseCase
    ) {
        this.createInventoryPort = createInventoryPort;
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @Override
    public CreateInventoryResult createInventory(CreateInventoryCommand command) {
        getProductByIdUseCase.getProductById(new GetProductByIdQuery(command.productId()));

        Inventory inventory = createInventoryPort.save(new Inventory(
                null,
                command.productId(),
                command.quantity(),
                command.reservedQuantity(),
                null,
                null
        ));
        return new CreateInventoryResult(inventory.id());
    }
}

package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryCommand;
import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryResult;
import com.tophantu.inventory.inventory.application.command.port.inbound.CreateInventoryUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.CreateInventoryPort;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.product.application.query.dto.CheckProductExistsQuery;
import com.tophantu.inventory.product.application.query.port.inbound.CheckProductExistsUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateInventoryHandler implements CreateInventoryUseCase {

    private final CreateInventoryPort createInventoryPort;
    private final CheckProductExistsUseCase checkProductExistsUseCase;

    public CreateInventoryHandler(
            CreateInventoryPort createInventoryPort,
            CheckProductExistsUseCase checkProductExistsUseCase
    ) {
        this.createInventoryPort = createInventoryPort;
        this.checkProductExistsUseCase = checkProductExistsUseCase;
    }

    @Override
    public CreateInventoryResult createInventory(CreateInventoryCommand command) {
        checkProductExistsUseCase.checkProductExists(new CheckProductExistsQuery(command.productId()));

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

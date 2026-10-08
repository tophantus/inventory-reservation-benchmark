package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryCommand;
import com.tophantu.inventory.inventory.application.command.port.outbound.CreateInventoryPort;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.product.application.query.port.inbound.CheckProductExistsUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateInventoryHandlerTest {

    @Test
    void initializesAvailableQuantityFromTheRequestedQuantity() {
        RecordingCreateInventoryPort inventoryPort = new RecordingCreateInventoryPort();
        CheckProductExistsUseCase checkProductExists = query -> { };
        CreateInventoryHandler handler = new CreateInventoryHandler(inventoryPort, checkProductExists);

        handler.createInventory(new CreateInventoryCommand(2L, 15L, 4L));

        assertEquals(15L, inventoryPort.savedInventory.availableQuantity());
    }

    private static final class RecordingCreateInventoryPort implements CreateInventoryPort {

        private Inventory savedInventory;

        @Override
        public Inventory save(Inventory inventory) {
            savedInventory = inventory;
            return new Inventory(
                    1L,
                    inventory.productId(),
                    inventory.quantity(),
                    inventory.reservedQuantity(),
                    inventory.availableQuantity(),
                    null,
                    null
            );
        }
    }
}

package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.AddInventoryUnitsCommand;
import com.tophantu.inventory.inventory.application.command.port.outbound.AddInventoryUnitsPort;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.model.Inventory;
import com.tophantu.inventory.inventory.domain.model.InventoryUnit;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddInventoryUnitsHandlerTest {

    @Test
    void addsExactlyTheRequestedNumberOfUnits() {
        FindInventoryByIdPort inventoryPort = new ExistingInventoryPort();
        RecordingAddInventoryUnitsPort unitsPort = new RecordingAddInventoryUnitsPort();
        AddInventoryUnitsHandler handler = new AddInventoryUnitsHandler(inventoryPort, unitsPort);

        handler.addInventoryUnits(new AddInventoryUnitsCommand(4L, 3L));

        assertEquals(3, unitsPort.inventoryUnits.size());
        assertEquals(List.of(4L, 4L, 4L), unitsPort.inventoryUnits.stream()
                .map(InventoryUnit::inventoryId)
                .toList());
    }

    private static final class ExistingInventoryPort implements FindInventoryByIdPort {

        @Override
        public Optional<Inventory> findById(Long inventoryId) {
            return Optional.of(new Inventory(inventoryId, 1L, 0L, 0L, null, null));
        }

        @Override
        public Optional<Inventory> findByProductId(Long productId) {
            return Optional.empty();
        }
    }

    private static final class RecordingAddInventoryUnitsPort implements AddInventoryUnitsPort {

        private List<InventoryUnit> inventoryUnits;

        @Override
        public void saveAll(List<InventoryUnit> inventoryUnits) {
            this.inventoryUnits = inventoryUnits;
        }
    }
}

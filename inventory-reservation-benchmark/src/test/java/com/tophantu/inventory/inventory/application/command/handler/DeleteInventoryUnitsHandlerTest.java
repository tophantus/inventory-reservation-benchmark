package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.DeleteInventoryUnitsCommand;
import com.tophantu.inventory.inventory.application.command.port.outbound.DeleteInventoryUnitsPort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteInventoryUnitsHandlerTest {

    @Test
    void delegatesTheExactUnitIdsForBatchDeletion() {
        RecordingDeleteInventoryUnitsPort unitsPort = new RecordingDeleteInventoryUnitsPort();
        DeleteInventoryUnitsHandler handler = new DeleteInventoryUnitsHandler(unitsPort);
        List<Long> unitIds = List.of(9L, 4L, 12L);

        handler.deleteInventoryUnits(new DeleteInventoryUnitsCommand(unitIds));

        assertEquals(unitIds, unitsPort.deletedUnitIds);
    }

    private static final class RecordingDeleteInventoryUnitsPort implements DeleteInventoryUnitsPort {

        private List<Long> deletedUnitIds;

        @Override
        public void deleteByIds(List<Long> unitIds) {
            deletedUnitIds = unitIds;
        }
    }
}

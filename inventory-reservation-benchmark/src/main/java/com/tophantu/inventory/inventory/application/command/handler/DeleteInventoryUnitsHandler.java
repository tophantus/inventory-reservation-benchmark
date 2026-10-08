package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.DeleteInventoryUnitsCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.DeleteInventoryUnitsUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.DeleteInventoryUnitsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteInventoryUnitsHandler implements DeleteInventoryUnitsUseCase {

    private final DeleteInventoryUnitsPort deleteInventoryUnitsPort;

    public DeleteInventoryUnitsHandler(DeleteInventoryUnitsPort deleteInventoryUnitsPort) {
        this.deleteInventoryUnitsPort = deleteInventoryUnitsPort;
    }

    @Override
    @Transactional
    public void deleteInventoryUnits(DeleteInventoryUnitsCommand command) {
        deleteInventoryUnitsPort.deleteByIds(command.unitIds());
    }
}

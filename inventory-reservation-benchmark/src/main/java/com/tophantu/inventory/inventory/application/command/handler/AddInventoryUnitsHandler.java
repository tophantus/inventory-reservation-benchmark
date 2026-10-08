package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.AddInventoryUnitsCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.AddInventoryUnitsUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.AddInventoryUnitsPort;
import com.tophantu.inventory.inventory.application.query.port.outbound.FindInventoryByIdPort;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.inventory.domain.model.InventoryUnit;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.LongStream;

@Service
public class AddInventoryUnitsHandler implements AddInventoryUnitsUseCase {

    private final FindInventoryByIdPort findInventoryByIdPort;
    private final AddInventoryUnitsPort addInventoryUnitsPort;

    public AddInventoryUnitsHandler(
            FindInventoryByIdPort findInventoryByIdPort,
            AddInventoryUnitsPort addInventoryUnitsPort
    ) {
        this.findInventoryByIdPort = findInventoryByIdPort;
        this.addInventoryUnitsPort = addInventoryUnitsPort;
    }

    @Override
    @Transactional
    public void addInventoryUnits(AddInventoryUnitsCommand command) {
        if (findInventoryByIdPort.findById(command.inventoryId()).isEmpty()) {
            throw new BusinessException(InventoryErrorCode.INVENTORY_NOT_FOUND);
        }

        List<InventoryUnit> inventoryUnits = LongStream.range(0, command.quantity())
                .mapToObj(ignored -> new InventoryUnit(null, command.inventoryId(), null))
                .toList();
        addInventoryUnitsPort.saveAll(inventoryUnits);
    }
}

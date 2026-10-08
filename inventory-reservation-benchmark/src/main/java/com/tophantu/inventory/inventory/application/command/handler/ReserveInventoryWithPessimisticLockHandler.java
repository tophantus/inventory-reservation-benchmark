package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.ReserveInventoryWithPessimisticLockUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.ReserveInventoryWithPessimisticLockPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveInventoryWithPessimisticLockHandler implements ReserveInventoryWithPessimisticLockUseCase {

    private final ReserveInventoryWithPessimisticLockPort reserveInventoryWithPessimisticLockPort;

    public ReserveInventoryWithPessimisticLockHandler(
            ReserveInventoryWithPessimisticLockPort reserveInventoryWithPessimisticLockPort
    ) {
        this.reserveInventoryWithPessimisticLockPort = reserveInventoryWithPessimisticLockPort;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void reserveInventory(ReserveInventoryCommand command) {
        reserveInventoryWithPessimisticLockPort.reserve(command.productId(), command.quantity());
    }
}

package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.ReserveInventoryWithPessimisticLockUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.ExpireHeldReservationsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ReserveInventoryWithPessimisticLockPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReserveInventoryWithPessimisticLockHandler implements ReserveInventoryWithPessimisticLockUseCase {

    private final ReserveInventoryWithPessimisticLockPort reserveInventoryWithPessimisticLockPort;
    private final ExpireHeldReservationsPort expireHeldReservationsPort;

    public ReserveInventoryWithPessimisticLockHandler(
            ReserveInventoryWithPessimisticLockPort reserveInventoryWithPessimisticLockPort,
            ExpireHeldReservationsPort expireHeldReservationsPort
    ) {
        this.reserveInventoryWithPessimisticLockPort = reserveInventoryWithPessimisticLockPort;
        this.expireHeldReservationsPort = expireHeldReservationsPort;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void reserveInventory(ReserveInventoryCommand command) {
        long expiredReservedQuantity = expireHeldReservationsPort.expireHeldReservations(
                command.productId(), LocalDateTime.now()
        );
        reserveInventoryWithPessimisticLockPort.reserve(
                command.productId(), expiredReservedQuantity, command.quantity()
        );
    }
}

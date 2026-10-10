package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryCommand;
import com.tophantu.inventory.inventory.application.command.port.outbound.ExpireHeldReservationsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ReserveInventoryWithPessimisticLockPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReserveInventoryWithPessimisticLockHandlerTest {

    @Test
    void releasesExpiredReservationsBeforeReservingInventory() {
        RecordingReservePort reservePort = new RecordingReservePort();
        ExpireHeldReservationsPort expirePort = (productId, now) -> 3L;
        ReserveInventoryWithPessimisticLockHandler handler = new ReserveInventoryWithPessimisticLockHandler(
                reservePort, expirePort
        );

        handler.reserveInventory(new ReserveInventoryCommand(9L, 5L));

        assertEquals(9L, reservePort.productId);
        assertEquals(3L, reservePort.expiredReservedQuantity);
        assertEquals(5L, reservePort.quantity);
    }

    private static final class RecordingReservePort implements ReserveInventoryWithPessimisticLockPort {

        private Long productId;
        private long expiredReservedQuantity;
        private long quantity;

        @Override
        public void reserve(Long productId, long expiredReservedQuantity, long quantity) {
            this.productId = productId;
            this.expiredReservedQuantity = expiredReservedQuantity;
            this.quantity = quantity;
        }
    }
}

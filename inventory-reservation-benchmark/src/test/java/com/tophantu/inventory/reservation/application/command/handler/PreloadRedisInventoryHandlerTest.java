package com.tophantu.inventory.reservation.application.command.handler;

import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.FindInventoryByProductIdUseCase;
import com.tophantu.inventory.reservation.application.command.dto.PreloadRedisInventoryCommand;
import com.tophantu.inventory.reservation.application.command.port.outbound.PreloadRedisInventoryPort;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreloadRedisInventoryHandlerTest {

    @Test
    void preloadsTheAvailableQuantityFromPostgresInventory() {
        FindInventoryByProductIdUseCase findInventory = query -> Optional.of(
                new InventoryQueryResult(10L, query.productId(), 20L, 7L, null, null)
        );
        RecordingPreloadPort preloadPort = new RecordingPreloadPort();
        PreloadRedisInventoryHandler handler = new PreloadRedisInventoryHandler(findInventory, preloadPort);

        handler.preload(new PreloadRedisInventoryCommand(3L));

        assertEquals(3L, preloadPort.productId);
        assertEquals(13L, preloadPort.availableQuantity);
    }

    private static final class RecordingPreloadPort implements PreloadRedisInventoryPort {

        private Long productId;
        private long availableQuantity;

        @Override
        public void preload(Long productId, long availableQuantity) {
            this.productId = productId;
            this.availableQuantity = availableQuantity;
        }
    }
}

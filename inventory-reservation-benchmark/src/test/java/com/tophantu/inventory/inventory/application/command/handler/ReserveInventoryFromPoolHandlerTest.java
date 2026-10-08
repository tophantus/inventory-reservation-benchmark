package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryFromPoolCommand;
import com.tophantu.inventory.inventory.application.command.port.outbound.AcquireInventoryPoolLockPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ChangeAvailableQuantityPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ExpireHeldReservationsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.InventoryUnitPoolPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.FindPoolInventoryPort;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.inventory.domain.model.PoolInventory;
import com.tophantu.inventory.shared.error.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReserveInventoryFromPoolHandlerTest {

    @Test
    void reservesDirectlyWhenThePoolHasEnoughUnits() {
        Scenario scenario = new Scenario(5L, 10L, 0L);

        scenario.reserve(3L);

        assertEquals(2L, scenario.pool.unitCount);
        assertEquals(0, scenario.lock.acquireCalls);
        assertEquals(0, scenario.pool.countCalls);
    }

    @Test
    void refillsFromAvailableQuantityWhenThePoolIsInsufficient() {
        Scenario scenario = new Scenario(0L, 10L, 0L);

        scenario.reserve(3L);

        assertEquals(0L, scenario.inventoryPort.inventory.availableQuantity());
        assertEquals(7L, scenario.pool.unitCount);
        assertEquals(1, scenario.lock.acquireCalls);
    }

    @Test
    void refillsExpiredHeldReservationsBeforeUsingAvailableQuantity() {
        Scenario scenario = new Scenario(0L, 10L, 6L);

        scenario.reserve(3L);

        assertEquals(0L, scenario.inventoryPort.inventory.availableQuantity());
        assertEquals(13L, scenario.pool.unitCount);
        assertEquals(1, scenario.expired.expireCalls);
    }

    @Test
    void returnsExpiredQuantityAbovePoolCapacityToAvailableQuantity() {
        Scenario scenario = new Scenario(0L, 4L, 1_200L);

        scenario.reserve(1L);

        assertEquals(204L, scenario.inventoryPort.inventory.availableQuantity());
        assertEquals(999L, scenario.pool.unitCount);
    }

    @Test
    void usesAvailableQuantityAfterExpiredQuantityDoesNotFillThePool() {
        Scenario scenario = new Scenario(0L, 10L, 2L);

        scenario.reserve(5L);

        assertEquals(0L, scenario.inventoryPort.inventory.availableQuantity());
        assertEquals(7L, scenario.pool.unitCount);
    }

    @Test
    void throwsWhenTotalStockCannotSatisfyTheRequestAfterRefill() {
        Scenario scenario = new Scenario(0L, 2L, 0L);

        BusinessException exception = assertThrows(BusinessException.class, () -> scenario.reserve(3L));

        assertEquals(InventoryErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY, exception.getErrorCode());
        assertEquals(2L, scenario.pool.unitCount);
    }

    private static final class Scenario {

        private final MutableInventoryPort inventoryPort;
        private final InMemoryPoolPort pool;
        private final RecordingLockPort lock;
        private final RecordingExpiredReservationsPort expired;
        private final ChangeAvailableQuantityPort availability;
        private final ReserveInventoryFromPoolHandler handler;

        private Scenario(long poolUnits, long availableQuantity, long expiredQuantity) {
            inventoryPort = new MutableInventoryPort(new PoolInventory(1L, 9L, availableQuantity));
            pool = new InMemoryPoolPort(poolUnits);
            lock = new RecordingLockPort();
            expired = new RecordingExpiredReservationsPort(expiredQuantity);
            availability = (inventoryId, delta) -> inventoryPort.inventory = new PoolInventory(
                    inventoryPort.inventory.id(), inventoryPort.inventory.productId(),
                    inventoryPort.inventory.availableQuantity() + delta
            );
            handler = new ReserveInventoryFromPoolHandler(inventoryPort, pool, lock, expired, availability);
        }

        private void reserve(long quantity) {
            handler.reserveInventory(new ReserveInventoryFromPoolCommand(9L, quantity, LocalDateTime.now().plusMinutes(1)));
        }
    }

    private static final class MutableInventoryPort implements FindPoolInventoryPort {

        private PoolInventory inventory;

        private MutableInventoryPort(PoolInventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public Optional<PoolInventory> findPoolByProductId(Long productId) {
            return Optional.of(inventory);
        }
    }

    private static final class InMemoryPoolPort implements InventoryUnitPoolPort {

        private long unitCount;
        private int countCalls;

        private InMemoryPoolPort(long unitCount) {
            this.unitCount = unitCount;
        }

        @Override
        public List<Long> findUnitIdsForAllocation(Long inventoryId, long quantity) {
            return LongStream.range(0, Math.min(unitCount, quantity)).boxed().toList();
        }

        @Override
        public void deleteByIds(List<Long> unitIds) {
            unitCount -= unitIds.size();
        }

        @Override
        public long countByInventoryId(Long inventoryId) {
            countCalls++;
            return unitCount;
        }

        @Override
        public void addUnits(Long inventoryId, long quantity) {
            unitCount += quantity;
        }
    }

    private static final class RecordingLockPort implements AcquireInventoryPoolLockPort {

        private int acquireCalls;

        @Override
        public void acquire(Long inventoryId) {
            acquireCalls++;
        }
    }

    private static final class RecordingExpiredReservationsPort implements ExpireHeldReservationsPort {

        private final long quantity;
        private int expireCalls;

        private RecordingExpiredReservationsPort(long quantity) {
            this.quantity = quantity;
        }

        @Override
        public long expireHeldReservations(Long productId, LocalDateTime now) {
            expireCalls++;
            return quantity;
        }
    }
}

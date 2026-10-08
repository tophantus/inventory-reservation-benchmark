package com.tophantu.inventory.inventory.application.command.handler;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryFromPoolCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.ReserveInventoryFromPoolUseCase;
import com.tophantu.inventory.inventory.application.command.port.outbound.AcquireInventoryPoolLockPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ChangeAvailableQuantityPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ExpireHeldReservationsPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.FindPoolInventoryPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.InventoryUnitPoolPort;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.inventory.domain.model.PoolInventory;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReserveInventoryFromPoolHandler implements ReserveInventoryFromPoolUseCase {

    private static final long POOL_CAPACITY = 1_000L;

    private final FindPoolInventoryPort findPoolInventoryPort;
    private final InventoryUnitPoolPort inventoryUnitPoolPort;
    private final AcquireInventoryPoolLockPort acquireInventoryPoolLockPort;
    private final ExpireHeldReservationsPort expireHeldReservationsPort;
    private final ChangeAvailableQuantityPort changeAvailableQuantityPort;

    public ReserveInventoryFromPoolHandler(
            FindPoolInventoryPort findPoolInventoryPort,
            InventoryUnitPoolPort inventoryUnitPoolPort,
            AcquireInventoryPoolLockPort acquireInventoryPoolLockPort,
            ExpireHeldReservationsPort expireHeldReservationsPort,
            ChangeAvailableQuantityPort changeAvailableQuantityPort
    ) {
        this.findPoolInventoryPort = findPoolInventoryPort;
        this.inventoryUnitPoolPort = inventoryUnitPoolPort;
        this.acquireInventoryPoolLockPort = acquireInventoryPoolLockPort;
        this.expireHeldReservationsPort = expireHeldReservationsPort;
        this.changeAvailableQuantityPort = changeAvailableQuantityPort;
    }

    @Override
    @Transactional
    public void reserveInventory(ReserveInventoryFromPoolCommand command) {
        PoolInventory inventory = findInventory(command.productId());

        if (tryAllocate(inventory.id(), command.quantity())) {
            return;
        }

        acquireInventoryPoolLockPort.acquire(inventory.id());
        refillPool(inventory.id(), command.productId(), LocalDateTime.now());
        if (!tryAllocate(inventory.id(), command.quantity())) {
            throw new BusinessException(InventoryErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY);
        }
    }

    private PoolInventory findInventory(Long productId) {
        return findPoolInventoryPort.findPoolByProductId(productId)
                .orElseThrow(() -> new BusinessException(InventoryErrorCode.INVENTORY_NOT_FOUND));
    }

    private boolean tryAllocate(Long inventoryId, long quantity) {
        List<Long> unitIds = inventoryUnitPoolPort.findUnitIdsForAllocation(inventoryId, quantity);
        if (unitIds.size() < quantity) {
            return false;
        }
        inventoryUnitPoolPort.deleteByIds(unitIds);
        return true;
    }

    private void refillPool(Long inventoryId, Long productId, LocalDateTime now) {
        long expiredQuantity = expireHeldReservationsPort.expireHeldReservations(productId, now);
        long poolCount = inventoryUnitPoolPort.countByInventoryId(inventoryId);
        long need = Math.max(0, POOL_CAPACITY - poolCount);
        long fromExpired = Math.min(need, expiredQuantity);
        long expiredExcess = expiredQuantity - fromExpired;

        PoolInventory inventory = findInventory(productId);
        long fromAvailable = Math.min(need - fromExpired, inventory.availableQuantity());
        long availabilityDelta = expiredExcess - fromAvailable;
        if (availabilityDelta != 0) {
            changeAvailableQuantityPort.changeAvailableQuantity(inventoryId, availabilityDelta);
        }

        long refillQuantity = fromExpired + fromAvailable;
        if (refillQuantity > 0) {
            inventoryUnitPoolPort.addUnits(inventoryId, refillQuantity);
        }
    }
}

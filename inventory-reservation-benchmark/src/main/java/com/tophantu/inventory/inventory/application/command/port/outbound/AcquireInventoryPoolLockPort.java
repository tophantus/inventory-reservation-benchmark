package com.tophantu.inventory.inventory.application.command.port.outbound;

public interface AcquireInventoryPoolLockPort {

    void acquire(Long inventoryId);
}

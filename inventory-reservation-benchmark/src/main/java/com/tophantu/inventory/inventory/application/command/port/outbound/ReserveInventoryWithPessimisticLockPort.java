package com.tophantu.inventory.inventory.application.command.port.outbound;

public interface ReserveInventoryWithPessimisticLockPort {

    void reserve(Long productId, long expiredReservedQuantity, long quantity);
}

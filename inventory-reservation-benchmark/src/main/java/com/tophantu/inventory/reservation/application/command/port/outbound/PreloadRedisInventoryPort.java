package com.tophantu.inventory.reservation.application.command.port.outbound;

public interface PreloadRedisInventoryPort {

    void preload(Long productId, long availableQuantity);
}

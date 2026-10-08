package com.tophantu.inventory.inventory.application.command.port.outbound;

public interface ChangeAvailableQuantityPort {

    void changeAvailableQuantity(Long inventoryId, long delta);
}

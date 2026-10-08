package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

public interface InventoryPoolProjection {

    Long getId();

    Long getProductId();

    Long getAvailableQuantity();
}

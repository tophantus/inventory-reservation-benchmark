package com.tophantu.inventory.product.application.command.port.outbound;

public interface CreateProductInventoryPort {

    void create(Long productId, long quantity);
}

package com.tophantu.inventory.product.application.command.port.outbound;

import com.tophantu.inventory.product.domain.model.Product;

public interface CreateProductPort {

    boolean existsBySku(String sku);

    Product save(Product product);
}

package com.tophantu.inventory.product.application.query.port.outbound;

import com.tophantu.inventory.product.application.query.dto.ProductInventoryQueryResult;

import java.util.Optional;

public interface FindProductInventoryPort {

    Optional<ProductInventoryQueryResult> findByProductId(Long productId);
}

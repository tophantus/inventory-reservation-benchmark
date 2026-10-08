package com.tophantu.inventory.product.application.query.port.outbound;

import com.tophantu.inventory.product.domain.model.Product;

import java.util.Optional;

public interface FindProductByIdPort {

    Optional<Product> findById(Long productId);
}

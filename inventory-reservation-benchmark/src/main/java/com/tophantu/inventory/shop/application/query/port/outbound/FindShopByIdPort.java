package com.tophantu.inventory.shop.application.query.port.outbound;

import com.tophantu.inventory.shop.domain.model.Shop;

import java.util.Optional;

public interface FindShopByIdPort {

    Optional<Shop> findById(Long shopId);
}

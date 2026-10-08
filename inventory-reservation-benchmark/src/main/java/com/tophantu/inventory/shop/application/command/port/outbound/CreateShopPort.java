package com.tophantu.inventory.shop.application.command.port.outbound;

import com.tophantu.inventory.shop.domain.model.Shop;

public interface CreateShopPort {

    Shop save(Shop shop);
}

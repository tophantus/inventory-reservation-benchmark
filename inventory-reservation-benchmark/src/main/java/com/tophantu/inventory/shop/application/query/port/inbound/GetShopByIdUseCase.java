package com.tophantu.inventory.shop.application.query.port.inbound;

import com.tophantu.inventory.shop.application.query.dto.GetShopByIdQuery;
import com.tophantu.inventory.shop.application.query.dto.ShopQueryResult;

public interface GetShopByIdUseCase {

    ShopQueryResult getShopById(GetShopByIdQuery query);
}

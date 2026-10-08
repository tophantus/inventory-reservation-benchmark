package com.tophantu.inventory.shop.application.query.handler;

import com.tophantu.inventory.shared.error.BusinessException;
import com.tophantu.inventory.shop.application.query.dto.GetShopByIdQuery;
import com.tophantu.inventory.shop.application.query.dto.ShopQueryResult;
import com.tophantu.inventory.shop.application.query.port.inbound.GetShopByIdUseCase;
import com.tophantu.inventory.shop.application.query.port.outbound.FindShopByIdPort;
import com.tophantu.inventory.shop.domain.exception.ShopErrorCode;
import com.tophantu.inventory.shop.domain.model.Shop;
import org.springframework.stereotype.Service;

@Service
public class GetShopByIdHandler implements GetShopByIdUseCase {

    private final FindShopByIdPort findShopByIdPort;

    public GetShopByIdHandler(FindShopByIdPort findShopByIdPort) {
        this.findShopByIdPort = findShopByIdPort;
    }

    @Override
    public ShopQueryResult getShopById(GetShopByIdQuery query) {
        Shop shop = findShopByIdPort.findById(query.shopId())
                .orElseThrow(() -> new BusinessException(ShopErrorCode.SHOP_NOT_FOUND));

        return new ShopQueryResult(
                shop.id(),
                shop.name(),
                shop.createdAt(),
                shop.updatedAt()
        );
    }
}

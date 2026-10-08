package com.tophantu.inventory.shop.application.command.handler;

import com.tophantu.inventory.shop.application.command.dto.CreateShopCommand;
import com.tophantu.inventory.shop.application.command.dto.CreateShopResult;
import com.tophantu.inventory.shop.application.command.port.inbound.CreateShopUseCase;
import com.tophantu.inventory.shop.application.command.port.outbound.CreateShopPort;
import com.tophantu.inventory.shop.domain.model.Shop;
import org.springframework.stereotype.Service;

@Service
public class CreateShopHandler implements CreateShopUseCase {

    private final CreateShopPort createShopPort;

    public CreateShopHandler(CreateShopPort createShopPort) {
        this.createShopPort = createShopPort;
    }

    @Override
    public CreateShopResult createShop(CreateShopCommand command) {
        Shop shop = createShopPort.save(new Shop(null, command.name(), null, null));
        return new CreateShopResult(shop.id());
    }
}

package com.tophantu.inventory.shop.application.command.port.inbound;

import com.tophantu.inventory.shop.application.command.dto.CreateShopCommand;
import com.tophantu.inventory.shop.application.command.dto.CreateShopResult;

public interface CreateShopUseCase {

    CreateShopResult createShop(CreateShopCommand command);
}

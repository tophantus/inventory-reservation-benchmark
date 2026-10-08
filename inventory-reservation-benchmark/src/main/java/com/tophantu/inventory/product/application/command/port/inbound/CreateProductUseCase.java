package com.tophantu.inventory.product.application.command.port.inbound;

import com.tophantu.inventory.product.application.command.dto.CreateProductCommand;
import com.tophantu.inventory.product.application.command.dto.CreateProductResult;

public interface CreateProductUseCase {

    CreateProductResult createProduct(CreateProductCommand command);
}

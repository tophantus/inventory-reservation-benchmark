package com.tophantu.inventory.product.application.command.handler;

import com.tophantu.inventory.product.application.command.dto.CreateProductCommand;
import com.tophantu.inventory.product.application.command.dto.CreateProductResult;
import com.tophantu.inventory.product.application.command.port.inbound.CreateProductUseCase;
import com.tophantu.inventory.product.application.command.port.outbound.CreateProductPort;
import com.tophantu.inventory.product.application.command.port.outbound.CreateProductInventoryPort;
import com.tophantu.inventory.product.domain.exception.ProductErrorCode;
import com.tophantu.inventory.product.domain.model.Product;
import com.tophantu.inventory.shared.error.BusinessException;
import com.tophantu.inventory.shop.application.query.dto.GetShopByIdQuery;
import com.tophantu.inventory.shop.application.query.port.inbound.GetShopByIdUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductHandler implements CreateProductUseCase {

    private final CreateProductPort createProductPort;
    private final CreateProductInventoryPort createProductInventoryPort;
    private final GetShopByIdUseCase getShopByIdUseCase;

    public CreateProductHandler(
            CreateProductPort createProductPort,
            CreateProductInventoryPort createProductInventoryPort,
            GetShopByIdUseCase getShopByIdUseCase
    ) {
        this.createProductPort = createProductPort;
        this.createProductInventoryPort = createProductInventoryPort;
        this.getShopByIdUseCase = getShopByIdUseCase;
    }

    @Override
    @Transactional
    public CreateProductResult createProduct(CreateProductCommand command) {
        getShopByIdUseCase.getShopById(new GetShopByIdQuery(command.shopId()));

        if (createProductPort.existsBySku(command.sku())) {
            throw new BusinessException(ProductErrorCode.SKU_ALREADY_EXISTS);
        }

        Product product = createProductPort.save(new Product(
                null,
                command.shopId(),
                command.name(),
                command.sku(),
                command.price(),
                null,
                null
        ));
        createProductInventoryPort.create(product.id(), command.quantity());
        return new CreateProductResult(product.id());
    }
}

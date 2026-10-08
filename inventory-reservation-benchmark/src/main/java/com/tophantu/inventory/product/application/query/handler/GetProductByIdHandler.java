package com.tophantu.inventory.product.application.query.handler;

import com.tophantu.inventory.product.application.query.dto.GetProductByIdQuery;
import com.tophantu.inventory.product.application.query.dto.ProductQueryResult;
import com.tophantu.inventory.product.application.query.dto.ProductInventoryQueryResult;
import com.tophantu.inventory.product.application.query.port.inbound.GetProductByIdUseCase;
import com.tophantu.inventory.product.application.query.port.outbound.FindProductByIdPort;
import com.tophantu.inventory.product.application.query.port.outbound.FindProductInventoryPort;
import com.tophantu.inventory.product.domain.exception.ProductErrorCode;
import com.tophantu.inventory.product.domain.model.Product;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class GetProductByIdHandler implements GetProductByIdUseCase {

    private final FindProductByIdPort findProductByIdPort;
    private final FindProductInventoryPort findProductInventoryPort;

    public GetProductByIdHandler(
            FindProductByIdPort findProductByIdPort,
            FindProductInventoryPort findProductInventoryPort
    ) {
        this.findProductByIdPort = findProductByIdPort;
        this.findProductInventoryPort = findProductInventoryPort;
    }

    @Override
    public ProductQueryResult getProductById(GetProductByIdQuery query) {
        Product product = findProductByIdPort.findById(query.productId())
                .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));
        ProductInventoryQueryResult inventory = findProductInventoryPort.findByProductId(product.id())
                .orElseThrow(() -> new BusinessException(ProductErrorCode.INVENTORY_NOT_FOUND));

        return new ProductQueryResult(
                product.id(),
                product.shopId(),
                product.name(),
                product.sku(),
                product.price(),
                inventory.quantity(),
                inventory.reservedQuantity(),
                inventory.availableQuantity(),
                product.createdAt(),
                product.updatedAt()
        );
    }
}

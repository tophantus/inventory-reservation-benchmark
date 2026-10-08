package com.tophantu.inventory.product.application.query.port.inbound;

import com.tophantu.inventory.product.application.query.dto.GetProductByIdQuery;
import com.tophantu.inventory.product.application.query.dto.ProductQueryResult;

public interface GetProductByIdUseCase {

    ProductQueryResult getProductById(GetProductByIdQuery query);
}

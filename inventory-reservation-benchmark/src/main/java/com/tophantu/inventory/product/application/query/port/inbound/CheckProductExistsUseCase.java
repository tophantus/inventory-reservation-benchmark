package com.tophantu.inventory.product.application.query.port.inbound;

import com.tophantu.inventory.product.application.query.dto.CheckProductExistsQuery;

public interface CheckProductExistsUseCase {

    void checkProductExists(CheckProductExistsQuery query);
}

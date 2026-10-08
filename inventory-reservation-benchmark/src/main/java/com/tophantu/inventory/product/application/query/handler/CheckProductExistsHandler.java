package com.tophantu.inventory.product.application.query.handler;

import com.tophantu.inventory.product.application.query.dto.CheckProductExistsQuery;
import com.tophantu.inventory.product.application.query.port.inbound.CheckProductExistsUseCase;
import com.tophantu.inventory.product.application.query.port.outbound.FindProductByIdPort;
import com.tophantu.inventory.product.domain.exception.ProductErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class CheckProductExistsHandler implements CheckProductExistsUseCase {

    private final FindProductByIdPort findProductByIdPort;

    public CheckProductExistsHandler(FindProductByIdPort findProductByIdPort) {
        this.findProductByIdPort = findProductByIdPort;
    }

    @Override
    public void checkProductExists(CheckProductExistsQuery query) {
        if (findProductByIdPort.findById(query.productId()).isEmpty()) {
            throw new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
    }
}

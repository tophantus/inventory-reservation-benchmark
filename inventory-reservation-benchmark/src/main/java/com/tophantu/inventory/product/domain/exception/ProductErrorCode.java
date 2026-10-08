package com.tophantu.inventory.product.domain.exception;

import com.tophantu.inventory.shared.error.ErrorCode;

public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND("PRODUCT_001", "Product not found"),
    SKU_ALREADY_EXISTS("PRODUCT_002", "SKU already exists"),
    INVENTORY_NOT_FOUND("PRODUCT_003", "Inventory not found for product");

    private final String code;
    private final String message;

    ProductErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

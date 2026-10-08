package com.tophantu.inventory.shop.domain.exception;

import com.tophantu.inventory.shared.error.ErrorCode;

public enum ShopErrorCode implements ErrorCode {
    SHOP_NOT_FOUND("SHOP_001", "Shop not found");

    private final String code;
    private final String message;

    ShopErrorCode(String code, String message) {
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

package com.tophantu.inventory.inventory.domain.exception;

import com.tophantu.inventory.shared.error.ErrorCode;

public enum InventoryErrorCode implements ErrorCode {
    INVENTORY_NOT_FOUND("INVENTORY_001", "Inventory not found"),
    NEGATIVE_QUANTITY("INVENTORY_002", "Quantity must not be negative"),
    NEGATIVE_RESERVED_QUANTITY("INVENTORY_003", "Reserved quantity must not be negative"),
    RESERVED_QUANTITY_EXCEEDS_QUANTITY("INVENTORY_004", "Reserved quantity must not exceed quantity"),
    INSUFFICIENT_AVAILABLE_QUANTITY("INVENTORY_005", "Insufficient available quantity"),
    NEGATIVE_AVAILABLE_QUANTITY("INVENTORY_006", "Available quantity must not be negative");

    private final String code;
    private final String message;

    InventoryErrorCode(String code, String message) {
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

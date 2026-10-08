package com.tophantu.inventory.reservation.domain.exception;

import com.tophantu.inventory.shared.error.ErrorCode;

public enum ReservationErrorCode implements ErrorCode {
    RESERVATION_NOT_FOUND("RESERVATION_001", "Reservation not found"),
    INVENTORY_NOT_FOUND("RESERVATION_002", "Inventory not found for product"),
    INSUFFICIENT_AVAILABLE_QUANTITY("RESERVATION_003", "Insufficient available quantity");

    private final String code;
    private final String message;

    ReservationErrorCode(String code, String message) {
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

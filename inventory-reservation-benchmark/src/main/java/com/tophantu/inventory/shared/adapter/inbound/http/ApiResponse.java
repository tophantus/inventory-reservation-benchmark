package com.tophantu.inventory.shared.adapter.inbound.http;

public record ApiResponse<T>(
        boolean success,
        String code,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "SUCCESS",
                "Success",
                data
        );
    }

    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {
        return new ApiResponse<>(
                true,
                "SUCCESS",
                message,
                data
        );
    }

    public static <T> ApiResponse<T> error(
            String code,
            String message
    ) {
        return new ApiResponse<>(
                false,
                code,
                message,
                null
        );
    }
}
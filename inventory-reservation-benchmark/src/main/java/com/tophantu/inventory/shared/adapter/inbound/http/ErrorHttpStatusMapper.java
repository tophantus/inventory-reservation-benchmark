package com.tophantu.inventory.shared.adapter.inbound.http;

import com.tophantu.inventory.shared.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorHttpStatusMapper {

    public HttpStatus map(ErrorCode errorCode) {
        return switch (errorCode.getCode()) {
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
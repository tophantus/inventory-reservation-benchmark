package com.tophantu.inventory.shared.adapter.inbound.http;

import com.tophantu.inventory.shared.error.BusinessException;
import com.tophantu.inventory.shared.error.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorHttpStatusMapper errorHttpStatusMapper;

    public GlobalExceptionHandler(
            ErrorHttpStatusMapper errorHttpStatusMapper
    ) {
        this.errorHttpStatusMapper = errorHttpStatusMapper;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException exception
    ) {
        ErrorCode errorCode = exception.getErrorCode();

        HttpStatus status = errorHttpStatusMapper.map(errorCode);

        return ResponseEntity
                .status(status)
                .body(
                        ApiResponse.error(
                                errorCode.getCode(),
                                errorCode.getMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.error(
                                "COMMON_001",
                                "Validation failed"
                        )
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.error(
                                "COMMON_002",
                                "Validation failed"
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(
            Exception exception
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.error(
                                "COMMON_500",
                                "Internal server error"
                        )
                );
    }
}
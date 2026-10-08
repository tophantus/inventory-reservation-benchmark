package com.tophantu.inventory.reservation.application.query.dto;

import com.tophantu.inventory.reservation.domain.enums.ReservationStatus;

import java.time.LocalDateTime;
public record ReservationQueryResult(
        Long id,
        Long customerId,
        Long productId,
        long quantity,
        ReservationStatus status,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

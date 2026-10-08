package com.tophantu.inventory.reservation.application.command.port.outbound;

import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;

import java.time.LocalDateTime;

public interface ReserveInventoryStrategy {

    ReservationStrategy strategy();

    void reserve(Long productId, long quantity, LocalDateTime expiresAt);
}

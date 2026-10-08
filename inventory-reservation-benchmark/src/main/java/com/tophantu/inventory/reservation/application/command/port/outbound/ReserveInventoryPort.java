package com.tophantu.inventory.reservation.application.command.port.outbound;

import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;

public interface ReserveInventoryPort {

    void reserve(Long productId, long quantity, ReservationStrategy strategy);
}

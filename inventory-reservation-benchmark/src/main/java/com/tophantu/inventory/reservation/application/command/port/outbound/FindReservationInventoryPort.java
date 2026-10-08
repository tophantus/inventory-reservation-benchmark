package com.tophantu.inventory.reservation.application.command.port.outbound;

import com.tophantu.inventory.reservation.application.command.dto.ReservationInventoryQueryResult;

import java.util.Optional;

public interface FindReservationInventoryPort {

    Optional<ReservationInventoryQueryResult> findByProductId(Long productId);
}

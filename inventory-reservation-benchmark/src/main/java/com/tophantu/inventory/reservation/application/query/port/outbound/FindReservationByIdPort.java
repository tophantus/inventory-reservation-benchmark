package com.tophantu.inventory.reservation.application.query.port.outbound;

import com.tophantu.inventory.reservation.domain.model.Reservation;
import java.util.Optional;

public interface FindReservationByIdPort {

    Optional<Reservation> findById(Long reservationId);
}

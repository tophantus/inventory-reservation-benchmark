package com.tophantu.inventory.reservation.application.command.port.outbound;

import com.tophantu.inventory.reservation.domain.model.Reservation;

public interface CreateReservationPort {

    Reservation save(Reservation reservation);
}

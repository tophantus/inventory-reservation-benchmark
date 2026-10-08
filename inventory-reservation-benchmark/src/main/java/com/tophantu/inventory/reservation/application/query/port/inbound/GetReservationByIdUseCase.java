package com.tophantu.inventory.reservation.application.query.port.inbound;

import com.tophantu.inventory.reservation.application.query.dto.GetReservationByIdQuery;
import com.tophantu.inventory.reservation.application.query.dto.ReservationQueryResult;

public interface GetReservationByIdUseCase {

    ReservationQueryResult getReservationById(GetReservationByIdQuery query);
}

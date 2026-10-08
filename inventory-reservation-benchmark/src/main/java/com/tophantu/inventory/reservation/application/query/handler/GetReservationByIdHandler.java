package com.tophantu.inventory.reservation.application.query.handler;

import com.tophantu.inventory.reservation.application.query.dto.GetReservationByIdQuery;
import com.tophantu.inventory.reservation.application.query.dto.ReservationQueryResult;
import com.tophantu.inventory.reservation.application.query.port.inbound.GetReservationByIdUseCase;
import com.tophantu.inventory.reservation.application.query.port.outbound.FindReservationByIdPort;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.reservation.domain.model.Reservation;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class GetReservationByIdHandler implements GetReservationByIdUseCase {

    private final FindReservationByIdPort findReservationByIdPort;

    public GetReservationByIdHandler(FindReservationByIdPort findReservationByIdPort) {
        this.findReservationByIdPort = findReservationByIdPort;
    }

    @Override
    public ReservationQueryResult getReservationById(GetReservationByIdQuery query) {
        Reservation reservation = findReservationByIdPort.findById(query.reservationId())
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        return new ReservationQueryResult(
                reservation.id(),
                reservation.customerId(),
                reservation.productId(),
                reservation.quantity(),
                reservation.status(),
                reservation.expiresAt(),
                reservation.createdAt(),
                reservation.updatedAt()
        );
    }
}

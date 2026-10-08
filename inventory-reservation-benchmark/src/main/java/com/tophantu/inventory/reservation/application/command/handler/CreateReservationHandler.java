package com.tophantu.inventory.reservation.application.command.handler;

import com.tophantu.inventory.reservation.application.command.dto.CreateReservationCommand;
import com.tophantu.inventory.reservation.application.command.dto.CreateReservationResult;
import com.tophantu.inventory.reservation.application.command.port.inbound.CreateReservationUseCase;
import com.tophantu.inventory.reservation.application.command.port.outbound.CreateReservationPort;
import com.tophantu.inventory.reservation.application.command.port.outbound.ReserveInventoryPort;
import com.tophantu.inventory.reservation.domain.enums.ReservationStatus;
import com.tophantu.inventory.reservation.domain.model.Reservation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreateReservationHandler implements CreateReservationUseCase {

    private final CreateReservationPort createReservationPort;
    private final ReserveInventoryPort reserveInventoryPort;

    public CreateReservationHandler(
            CreateReservationPort createReservationPort,
            ReserveInventoryPort reserveInventoryPort
    ) {
        this.createReservationPort = createReservationPort;
        this.reserveInventoryPort = reserveInventoryPort;
    }

    @Override
    @Transactional
    public CreateReservationResult createReservation(CreateReservationCommand command) {
        reserveInventoryPort.reserve(command.productId(), command.quantity(), command.expiresAt(), command.strategy());

        Reservation reservation = createReservationPort.save(
                new Reservation(
                        null,
                        command.customerId(),
                        command.productId(),
                        command.quantity(),
                        ReservationStatus.HELD,
                        command.expiresAt(),
                        null,
                        null
                )
        );

        return new CreateReservationResult(reservation.id());
    }
}

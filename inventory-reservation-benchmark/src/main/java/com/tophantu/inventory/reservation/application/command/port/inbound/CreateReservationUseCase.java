package com.tophantu.inventory.reservation.application.command.port.inbound;

import com.tophantu.inventory.reservation.application.command.dto.CreateReservationCommand;
import com.tophantu.inventory.reservation.application.command.dto.CreateReservationResult;

public interface CreateReservationUseCase {

    CreateReservationResult createReservation(CreateReservationCommand command);
}

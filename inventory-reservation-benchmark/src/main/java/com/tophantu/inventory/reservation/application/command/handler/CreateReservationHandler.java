package com.tophantu.inventory.reservation.application.command.handler;

import com.tophantu.inventory.reservation.application.command.dto.CreateReservationCommand;
import com.tophantu.inventory.reservation.application.command.dto.CreateReservationResult;
import com.tophantu.inventory.reservation.application.command.dto.ReservationInventoryQueryResult;
import com.tophantu.inventory.reservation.application.command.port.inbound.CreateReservationUseCase;
import com.tophantu.inventory.reservation.application.command.port.outbound.CreateReservationPort;
import com.tophantu.inventory.reservation.application.command.port.outbound.FindReservationInventoryPort;
import com.tophantu.inventory.reservation.domain.enums.ReservationStatus;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.reservation.domain.model.Reservation;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreateReservationHandler implements CreateReservationUseCase {

    private final CreateReservationPort createReservationPort;
    private final FindReservationInventoryPort findReservationInventoryPort;

    public CreateReservationHandler(
            CreateReservationPort createReservationPort,
            FindReservationInventoryPort findReservationInventoryPort
    ) {
        this.createReservationPort = createReservationPort;
        this.findReservationInventoryPort = findReservationInventoryPort;
    }

    @Override
    @Transactional
    public CreateReservationResult createReservation(CreateReservationCommand command) {
        validateAvailableQuantity(command.productId(), command.quantity());

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

    private void validateAvailableQuantity(Long productId, long quantity) {
        ReservationInventoryQueryResult inventory = findReservationInventoryPort.findByProductId(productId)
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.INVENTORY_NOT_FOUND));
        if (inventory.availableQuantity() < quantity) {
            throw new BusinessException(ReservationErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY);
        }
    }
}

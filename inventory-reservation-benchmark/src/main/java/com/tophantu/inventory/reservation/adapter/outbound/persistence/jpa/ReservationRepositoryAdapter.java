package com.tophantu.inventory.reservation.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.reservation.application.command.port.outbound.CreateReservationPort;
import com.tophantu.inventory.inventory.application.command.port.outbound.ExpireHeldReservationsPort;
import com.tophantu.inventory.reservation.application.query.port.outbound.FindReservationByIdPort;
import com.tophantu.inventory.reservation.domain.enums.ReservationStatus;
import com.tophantu.inventory.reservation.domain.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public class ReservationRepositoryAdapter implements CreateReservationPort, FindReservationByIdPort, ExpireHeldReservationsPort {

    private final ReservationJpaRepository reservationJpaRepository;

    public ReservationRepositoryAdapter(ReservationJpaRepository reservationJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return ReservationJpaMapper.toDomain(
                reservationJpaRepository.save(ReservationJpaMapper.toEntity(reservation))
        );
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId)
                .map(ReservationJpaMapper::toDomain);
    }

    @Override
    public long expireHeldReservations(Long productId, LocalDateTime now) {
        return reservationJpaRepository
                .findByProductIdAndStatusAndExpiresAtLessThanEqual(productId, ReservationStatus.HELD, now)
                .stream()
                .peek(ReservationJpaEntity::expire)
                .mapToLong(ReservationJpaEntity::getQuantity)
                .sum();
    }

}

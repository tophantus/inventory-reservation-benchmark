package com.tophantu.inventory.reservation.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.reservation.application.command.port.outbound.CreateReservationPort;
import com.tophantu.inventory.reservation.application.query.port.outbound.FindReservationByIdPort;
import com.tophantu.inventory.reservation.domain.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReservationRepositoryAdapter implements CreateReservationPort, FindReservationByIdPort {

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

}

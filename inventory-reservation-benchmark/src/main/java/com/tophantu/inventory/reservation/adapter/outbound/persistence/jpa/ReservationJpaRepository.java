package com.tophantu.inventory.reservation.adapter.outbound.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tophantu.inventory.reservation.domain.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    List<ReservationJpaEntity> findByProductIdAndStatusAndExpiresAtLessThanEqual(
            Long productId,
            ReservationStatus status,
            LocalDateTime expiresAt
    );
}

package com.tophantu.inventory.reservation.adapter.outbound.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
}

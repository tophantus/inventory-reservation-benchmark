package com.tophantu.inventory.reservation.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.reservation.domain.model.Reservation;

public final class ReservationJpaMapper {

    private ReservationJpaMapper() {
    }

    public static Reservation toDomain(ReservationJpaEntity entity) {
        return new Reservation(
                entity.getId(),
                entity.getCustomerId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getStatus(),
                entity.getExpiresAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static ReservationJpaEntity toEntity(Reservation reservation) {
        return new ReservationJpaEntity(
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

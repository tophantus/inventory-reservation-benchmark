package com.tophantu.inventory.reservation.application.command.dto;

public enum ReservationStrategy {
    POSTGRES_PESSIMISTIC,
    REDIS,
    POSTGRES_POOL
}

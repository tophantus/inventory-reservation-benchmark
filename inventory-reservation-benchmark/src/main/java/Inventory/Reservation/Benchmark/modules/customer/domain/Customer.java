package Inventory.Reservation.Benchmark.modules.customer.domain;

import java.time.LocalDateTime;

public record Customer(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

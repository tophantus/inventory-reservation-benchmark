package Inventory.Reservation.Benchmark.modules.customer.adapter.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}

package Inventory.Reservation.Benchmark.modules.customer.adapter.outbound.persistence;

import Inventory.Reservation.Benchmark.modules.customer.domain.Customer;

public final class CustomerJpaMapper {

    private CustomerJpaMapper() {
    }

    public static Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static CustomerJpaEntity toEntity(Customer customer) {
        return new CustomerJpaEntity(
                customer.id(),
                customer.name(),
                customer.createdAt(),
                customer.updatedAt()
        );
    }
}

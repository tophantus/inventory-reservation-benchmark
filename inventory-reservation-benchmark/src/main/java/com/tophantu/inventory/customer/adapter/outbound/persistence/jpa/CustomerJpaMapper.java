package com.tophantu.inventory.customer.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.customer.domain.model.Customer;

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

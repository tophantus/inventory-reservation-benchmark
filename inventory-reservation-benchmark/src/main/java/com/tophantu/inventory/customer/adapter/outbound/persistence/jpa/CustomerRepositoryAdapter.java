package com.tophantu.inventory.customer.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.customer.application.command.port.outbound.CreateCustomerPort;
import com.tophantu.inventory.customer.application.query.port.outbound.FindCustomerByIdPort;
import com.tophantu.inventory.customer.domain.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CreateCustomerPort, FindCustomerByIdPort {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = CustomerJpaMapper.toEntity(customer);
        return CustomerJpaMapper.toDomain(customerJpaRepository.save(entity));
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id)
                .map(CustomerJpaMapper::toDomain);
    }
}

package com.tophantu.inventory.customer.application.query.port.outbound;

import com.tophantu.inventory.customer.domain.model.Customer;

import java.util.Optional;

public interface FindCustomerByIdPort {

    Optional<Customer> findById(Long customerId);
}

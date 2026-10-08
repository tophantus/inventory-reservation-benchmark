package com.tophantu.inventory.customer.application.command.port.outbound;

import com.tophantu.inventory.customer.domain.model.Customer;

public interface CreateCustomerPort {

    Customer save(Customer customer);
}

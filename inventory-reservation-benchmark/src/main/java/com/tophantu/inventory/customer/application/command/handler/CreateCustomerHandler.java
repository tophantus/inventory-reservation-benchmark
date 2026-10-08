package com.tophantu.inventory.customer.application.command.handler;

import com.tophantu.inventory.customer.application.command.dto.CreateCustomerCommand;
import com.tophantu.inventory.customer.application.command.dto.CreateCustomerResult;
import com.tophantu.inventory.customer.application.command.port.inbound.CreateCustomerUseCase;
import com.tophantu.inventory.customer.application.command.port.outbound.CreateCustomerPort;
import com.tophantu.inventory.customer.domain.model.Customer;

public class CreateCustomerHandler implements CreateCustomerUseCase {

    private final CreateCustomerPort createCustomerPort;

    public CreateCustomerHandler(CreateCustomerPort createCustomerPort) {
        this.createCustomerPort = createCustomerPort;
    }

    @Override
    public CreateCustomerResult createCustomer(CreateCustomerCommand command) {
        Customer customer = createCustomerPort.save(new Customer(null, command.name(), null, null));
        return new CreateCustomerResult(customer.id());
    }
}

package com.tophantu.inventory.customer.application.command.port.inbound;

import com.tophantu.inventory.customer.application.command.dto.CreateCustomerCommand;
import com.tophantu.inventory.customer.application.command.dto.CreateCustomerResult;

public interface CreateCustomerUseCase {

    CreateCustomerResult createCustomer(CreateCustomerCommand command);
}

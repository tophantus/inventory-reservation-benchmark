package com.tophantu.inventory.customer.application.query.handler;

import com.tophantu.inventory.customer.application.query.dto.CustomerQueryResult;
import com.tophantu.inventory.customer.application.query.dto.GetCustomerByIdQuery;
import com.tophantu.inventory.customer.application.query.port.inbound.GetCustomerByIdUseCase;
import com.tophantu.inventory.customer.application.query.port.outbound.FindCustomerByIdPort;

import java.util.Optional;

public class GetCustomerByIdHandler implements GetCustomerByIdUseCase {

    private final FindCustomerByIdPort findCustomerByIdPort;

    public GetCustomerByIdHandler(FindCustomerByIdPort findCustomerByIdPort) {
        this.findCustomerByIdPort = findCustomerByIdPort;
    }

    @Override
    public Optional<CustomerQueryResult> getCustomerById(GetCustomerByIdQuery query) {
        return findCustomerByIdPort.findById(query.customerId())
                .map(customer -> new CustomerQueryResult(
                        customer.id(),
                        customer.name(),
                        customer.createdAt(),
                        customer.updatedAt()
                ));
    }
}

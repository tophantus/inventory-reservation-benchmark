package com.tophantu.inventory.customer.application.query.port.inbound;

import com.tophantu.inventory.customer.application.query.dto.CustomerQueryResult;
import com.tophantu.inventory.customer.application.query.dto.GetCustomerByIdQuery;

import java.util.Optional;

public interface GetCustomerByIdUseCase {

    Optional<CustomerQueryResult> getCustomerById(GetCustomerByIdQuery query);
}

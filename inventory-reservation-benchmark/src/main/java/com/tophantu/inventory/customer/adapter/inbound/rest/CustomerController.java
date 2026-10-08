package com.tophantu.inventory.customer.adapter.inbound.rest;

import com.tophantu.inventory.customer.application.command.dto.CreateCustomerCommand;
import com.tophantu.inventory.customer.application.command.dto.CreateCustomerResult;
import com.tophantu.inventory.customer.application.command.port.inbound.CreateCustomerUseCase;
import com.tophantu.inventory.shared.adapter.inbound.http.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCustomerResult>> createCustomer(
            @RequestBody CreateCustomerCommand command
    ) {
        CreateCustomerResult result = createCustomerUseCase.createCustomer(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }
}

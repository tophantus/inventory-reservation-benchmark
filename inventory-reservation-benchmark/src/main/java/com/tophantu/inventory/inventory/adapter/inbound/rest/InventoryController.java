package com.tophantu.inventory.inventory.adapter.inbound.rest;

import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryCommand;
import com.tophantu.inventory.inventory.application.command.dto.CreateInventoryResult;
import com.tophantu.inventory.inventory.application.command.port.inbound.CreateInventoryUseCase;
import com.tophantu.inventory.inventory.application.query.dto.GetInventoryByIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.GetInventoryByIdUseCase;
import com.tophantu.inventory.shared.adapter.inbound.http.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/inventories")
public class InventoryController {

    private final CreateInventoryUseCase createInventoryUseCase;
    private final GetInventoryByIdUseCase getInventoryByIdUseCase;

    public InventoryController(
            CreateInventoryUseCase createInventoryUseCase,
            GetInventoryByIdUseCase getInventoryByIdUseCase
    ) {
        this.createInventoryUseCase = createInventoryUseCase;
        this.getInventoryByIdUseCase = getInventoryByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateInventoryResult>> createInventory(
            @Valid @RequestBody CreateInventoryCommand command
    ) {
        CreateInventoryResult result = createInventoryUseCase.createInventory(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse<InventoryQueryResult>> getInventoryById(
            @PathVariable @Positive Long inventoryId
    ) {
        InventoryQueryResult result = getInventoryByIdUseCase.getInventoryById(
                new GetInventoryByIdQuery(inventoryId)
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

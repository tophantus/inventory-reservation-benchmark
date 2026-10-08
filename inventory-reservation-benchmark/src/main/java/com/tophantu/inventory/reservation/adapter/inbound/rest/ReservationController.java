package com.tophantu.inventory.reservation.adapter.inbound.rest;

import com.tophantu.inventory.reservation.application.command.dto.CreateReservationCommand;
import com.tophantu.inventory.reservation.application.command.dto.CreateReservationResult;
import com.tophantu.inventory.reservation.application.command.dto.PreloadRedisInventoryCommand;
import com.tophantu.inventory.reservation.application.command.port.inbound.CreateReservationUseCase;
import com.tophantu.inventory.reservation.application.command.port.inbound.PreloadRedisInventoryUseCase;
import com.tophantu.inventory.reservation.application.query.dto.GetReservationByIdQuery;
import com.tophantu.inventory.reservation.application.query.dto.ReservationQueryResult;
import com.tophantu.inventory.reservation.application.query.port.inbound.GetReservationByIdUseCase;
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
@RequestMapping("/v1/reservations")
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;
    private final PreloadRedisInventoryUseCase preloadRedisInventoryUseCase;
    private final GetReservationByIdUseCase getReservationByIdUseCase;

    public ReservationController(
            CreateReservationUseCase createReservationUseCase,
            PreloadRedisInventoryUseCase preloadRedisInventoryUseCase,
            GetReservationByIdUseCase getReservationByIdUseCase
    ) {
        this.createReservationUseCase = createReservationUseCase;
        this.preloadRedisInventoryUseCase = preloadRedisInventoryUseCase;
        this.getReservationByIdUseCase = getReservationByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateReservationResult>> createReservation(
            @Valid @RequestBody CreateReservationCommand command
    ) {
        CreateReservationResult result = createReservationUseCase.createReservation(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }

    @PostMapping("/redis-inventory/{productId}/preload")
    public ResponseEntity<ApiResponse<Void>> preloadRedisInventory(@PathVariable @Positive Long productId) {
        preloadRedisInventoryUseCase.preload(new PreloadRedisInventoryCommand(productId));
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationQueryResult>> getReservationById(
            @PathVariable @Positive Long reservationId
    ) {
        ReservationQueryResult result = getReservationByIdUseCase.getReservationById(
                new GetReservationByIdQuery(reservationId)
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

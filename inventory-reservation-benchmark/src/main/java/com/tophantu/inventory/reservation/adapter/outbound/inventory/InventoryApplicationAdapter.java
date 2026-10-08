package com.tophantu.inventory.reservation.adapter.outbound.inventory;

import com.tophantu.inventory.inventory.application.query.dto.FindInventoryByProductIdQuery;
import com.tophantu.inventory.inventory.application.query.dto.InventoryQueryResult;
import com.tophantu.inventory.inventory.application.query.port.inbound.FindInventoryByProductIdUseCase;
import com.tophantu.inventory.product.application.query.dto.CheckProductExistsQuery;
import com.tophantu.inventory.product.application.query.port.inbound.CheckProductExistsUseCase;
import com.tophantu.inventory.reservation.application.command.dto.ReservationInventoryQueryResult;
import com.tophantu.inventory.reservation.application.command.port.outbound.FindReservationInventoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("reservationInventoryApplicationAdapter")
public class InventoryApplicationAdapter implements FindReservationInventoryPort {

    private final CheckProductExistsUseCase checkProductExistsUseCase;
    private final FindInventoryByProductIdUseCase findInventoryByProductIdUseCase;

    public InventoryApplicationAdapter(
            CheckProductExistsUseCase checkProductExistsUseCase,
            FindInventoryByProductIdUseCase findInventoryByProductIdUseCase
    ) {
        this.checkProductExistsUseCase = checkProductExistsUseCase;
        this.findInventoryByProductIdUseCase = findInventoryByProductIdUseCase;
    }

    @Override
    public Optional<ReservationInventoryQueryResult> findByProductId(Long productId) {
        checkProductExistsUseCase.checkProductExists(new CheckProductExistsQuery(productId));
        return findInventoryByProductIdUseCase.findInventoryByProductId(new FindInventoryByProductIdQuery(productId))
                .map(this::toReservationInventoryQueryResult);
    }

    private ReservationInventoryQueryResult toReservationInventoryQueryResult(InventoryQueryResult inventory) {
        return new ReservationInventoryQueryResult(inventory.quantity(), inventory.reservedQuantity());
    }
}

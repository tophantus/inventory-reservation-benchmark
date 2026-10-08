package com.tophantu.inventory.reservation.adapter.outbound.inventory;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryFromPoolCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.ReserveInventoryFromPoolUseCase;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;
import com.tophantu.inventory.reservation.application.command.port.outbound.ReserveInventoryStrategy;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostgresPoolReserveInventoryAdapter implements ReserveInventoryStrategy {

    private final ReserveInventoryFromPoolUseCase reserveInventoryFromPoolUseCase;

    public PostgresPoolReserveInventoryAdapter(ReserveInventoryFromPoolUseCase reserveInventoryFromPoolUseCase) {
        this.reserveInventoryFromPoolUseCase = reserveInventoryFromPoolUseCase;
    }

    @Override
    public ReservationStrategy strategy() {
        return ReservationStrategy.POSTGRES_POOL;
    }

    @Override
    public void reserve(Long productId, long quantity, LocalDateTime expiresAt) {
        try {
            reserveInventoryFromPoolUseCase.reserveInventory(
                    new ReserveInventoryFromPoolCommand(productId, quantity, expiresAt)
            );
        } catch (BusinessException exception) {
            throw mapInventoryException(exception);
        }
    }

    private BusinessException mapInventoryException(BusinessException exception) {
        if (exception.getErrorCode() == InventoryErrorCode.INVENTORY_NOT_FOUND) {
            return new BusinessException(ReservationErrorCode.INVENTORY_NOT_FOUND);
        }
        if (exception.getErrorCode() == InventoryErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY) {
            return new BusinessException(ReservationErrorCode.INSUFFICIENT_AVAILABLE_QUANTITY);
        }
        return exception;
    }
}

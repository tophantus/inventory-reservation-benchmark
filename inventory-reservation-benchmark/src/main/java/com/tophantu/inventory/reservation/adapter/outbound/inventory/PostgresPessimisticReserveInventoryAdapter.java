package com.tophantu.inventory.reservation.adapter.outbound.inventory;

import com.tophantu.inventory.inventory.application.command.dto.ReserveInventoryCommand;
import com.tophantu.inventory.inventory.application.command.port.inbound.ReserveInventoryWithPessimisticLockUseCase;
import com.tophantu.inventory.inventory.domain.exception.InventoryErrorCode;
import com.tophantu.inventory.product.application.query.dto.CheckProductExistsQuery;
import com.tophantu.inventory.product.application.query.port.inbound.CheckProductExistsUseCase;
import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;
import com.tophantu.inventory.reservation.application.command.port.outbound.ReserveInventoryStrategy;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class PostgresPessimisticReserveInventoryAdapter implements ReserveInventoryStrategy {

    private final CheckProductExistsUseCase checkProductExistsUseCase;
    private final ReserveInventoryWithPessimisticLockUseCase reserveInventoryWithPessimisticLockUseCase;

    public PostgresPessimisticReserveInventoryAdapter(
            CheckProductExistsUseCase checkProductExistsUseCase,
            ReserveInventoryWithPessimisticLockUseCase reserveInventoryWithPessimisticLockUseCase
    ) {
        this.checkProductExistsUseCase = checkProductExistsUseCase;
        this.reserveInventoryWithPessimisticLockUseCase = reserveInventoryWithPessimisticLockUseCase;
    }

    @Override
    public ReservationStrategy strategy() {
        return ReservationStrategy.POSTGRES_PESSIMISTIC;
    }

    @Override
    public void reserve(Long productId, long quantity) {
        checkProductExistsUseCase.checkProductExists(new CheckProductExistsQuery(productId));
        try {
            reserveInventoryWithPessimisticLockUseCase.reserveInventory(
                    new ReserveInventoryCommand(productId, quantity)
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

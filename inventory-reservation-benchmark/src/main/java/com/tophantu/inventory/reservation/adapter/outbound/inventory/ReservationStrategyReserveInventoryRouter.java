package com.tophantu.inventory.reservation.adapter.outbound.inventory;

import com.tophantu.inventory.reservation.application.command.dto.ReservationStrategy;
import com.tophantu.inventory.reservation.application.command.port.outbound.ReserveInventoryPort;
import com.tophantu.inventory.reservation.application.command.port.outbound.ReserveInventoryStrategy;
import com.tophantu.inventory.reservation.domain.exception.ReservationErrorCode;
import com.tophantu.inventory.shared.error.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationStrategyReserveInventoryRouter implements ReserveInventoryPort {

    private final List<ReserveInventoryStrategy> reserveInventoryStrategies;

    public ReservationStrategyReserveInventoryRouter(List<ReserveInventoryStrategy> reserveInventoryStrategies) {
        this.reserveInventoryStrategies = reserveInventoryStrategies;
    }

    @Override
    public void reserve(Long productId, long quantity, LocalDateTime expiresAt, ReservationStrategy strategy) {
        reserveInventoryStrategies.stream()
                .filter(reserveInventoryStrategy -> reserveInventoryStrategy.strategy() == strategy)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.UNSUPPORTED_STRATEGY))
                .reserve(productId, quantity, expiresAt);
    }
}

package com.tophantu.inventory.inventory.application.command.port.outbound;

import java.time.LocalDateTime;

public interface ExpireHeldReservationsPort {

    long expireHeldReservations(Long productId, LocalDateTime now);
}

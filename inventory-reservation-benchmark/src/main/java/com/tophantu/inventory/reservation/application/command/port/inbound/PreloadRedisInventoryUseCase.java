package com.tophantu.inventory.reservation.application.command.port.inbound;

import com.tophantu.inventory.reservation.application.command.dto.PreloadRedisInventoryCommand;

public interface PreloadRedisInventoryUseCase {

    void preload(PreloadRedisInventoryCommand command);
}

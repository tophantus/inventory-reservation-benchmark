package com.tophantu.inventory.inventory.application.command.dto;

import java.time.LocalDateTime;

public record ReserveInventoryFromPoolCommand(
        Long productId,
        long quantity,
        LocalDateTime expiresAt
) {
}

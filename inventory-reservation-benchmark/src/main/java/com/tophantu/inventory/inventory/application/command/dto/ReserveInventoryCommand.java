package com.tophantu.inventory.inventory.application.command.dto;

public record ReserveInventoryCommand(Long productId, long quantity) {
}

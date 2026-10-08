package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.inventory.domain.model.Inventory;

public final class InventoryJpaMapper {

    private InventoryJpaMapper() {
    }

    public static Inventory toDomain(InventoryJpaEntity entity) {
        return new Inventory(
                entity.getId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getReservedQuantity(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static InventoryJpaEntity toEntity(Inventory inventory) {
        return new InventoryJpaEntity(
                inventory.id(),
                inventory.productId(),
                inventory.quantity(),
                inventory.reservedQuantity(),
                inventory.createdAt(),
                inventory.updatedAt()
        );
    }
}

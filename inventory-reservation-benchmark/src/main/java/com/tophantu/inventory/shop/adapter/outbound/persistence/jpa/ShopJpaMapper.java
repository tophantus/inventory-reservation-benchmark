package com.tophantu.inventory.shop.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.shop.domain.model.Shop;

public final class ShopJpaMapper {

    private ShopJpaMapper() {
    }

    public static Shop toDomain(ShopJpaEntity entity) {
        return new Shop(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static ShopJpaEntity toEntity(Shop shop) {
        return new ShopJpaEntity(
                shop.id(),
                shop.name(),
                shop.createdAt(),
                shop.updatedAt()
        );
    }
}

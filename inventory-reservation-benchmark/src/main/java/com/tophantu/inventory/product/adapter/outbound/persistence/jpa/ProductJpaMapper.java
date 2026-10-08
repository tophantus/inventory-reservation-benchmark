package com.tophantu.inventory.product.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.product.domain.model.Product;

public final class ProductJpaMapper {

    private ProductJpaMapper() {
    }

    public static Product toDomain(ProductJpaEntity entity) {
        return new Product(
                entity.getId(),
                entity.getShopId(),
                entity.getName(),
                entity.getSku(),
                entity.getPrice(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static ProductJpaEntity toEntity(Product product) {
        return new ProductJpaEntity(
                product.id(),
                product.shopId(),
                product.name(),
                product.sku(),
                product.price(),
                product.createdAt(),
                product.updatedAt()
        );
    }
}

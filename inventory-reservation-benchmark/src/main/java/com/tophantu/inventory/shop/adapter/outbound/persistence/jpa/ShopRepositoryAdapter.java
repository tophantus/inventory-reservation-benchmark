package com.tophantu.inventory.shop.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.shop.application.command.port.outbound.CreateShopPort;
import com.tophantu.inventory.shop.application.query.port.outbound.FindShopByIdPort;
import com.tophantu.inventory.shop.domain.model.Shop;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShopRepositoryAdapter implements CreateShopPort, FindShopByIdPort {

    private final ShopJpaRepository shopJpaRepository;

    public ShopRepositoryAdapter(ShopJpaRepository shopJpaRepository) {
        this.shopJpaRepository = shopJpaRepository;
    }

    @Override
    public Shop save(Shop shop) {
        return ShopJpaMapper.toDomain(shopJpaRepository.save(ShopJpaMapper.toEntity(shop)));
    }

    @Override
    public Optional<Shop> findById(Long shopId) {
        return shopJpaRepository.findById(shopId)
                .map(ShopJpaMapper::toDomain);
    }
}

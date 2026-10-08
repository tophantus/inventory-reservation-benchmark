package com.tophantu.inventory.product.adapter.outbound.persistence.jpa;

import com.tophantu.inventory.product.application.command.port.outbound.CreateProductPort;
import com.tophantu.inventory.product.application.query.port.outbound.FindProductByIdPort;
import com.tophantu.inventory.product.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryAdapter implements CreateProductPort, FindProductByIdPort {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public boolean existsBySku(String sku) {
        return productJpaRepository.existsBySku(sku);
    }

    @Override
    public Product save(Product product) {
        return ProductJpaMapper.toDomain(productJpaRepository.save(ProductJpaMapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId)
                .map(ProductJpaMapper::toDomain);
    }
}

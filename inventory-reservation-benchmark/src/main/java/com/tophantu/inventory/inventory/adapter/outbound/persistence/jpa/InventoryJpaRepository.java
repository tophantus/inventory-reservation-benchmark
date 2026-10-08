package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {

    Optional<InventoryJpaEntity> findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select inventory from InventoryJpaEntity inventory where inventory.productId = :productId")
    Optional<InventoryJpaEntity> findByProductIdForUpdate(@Param("productId") Long productId);

    @Query("""
            select inventory.id as id,
                   inventory.productId as productId,
                   inventory.availableQuantity as availableQuantity
            from InventoryJpaEntity inventory
            where inventory.productId = :productId
            """)
    Optional<InventoryPoolProjection> findPoolInventoryByProductId(@Param("productId") Long productId);

    @org.springframework.data.jpa.repository.Modifying
    @Query("""
            update InventoryJpaEntity inventory
            set inventory.availableQuantity = inventory.availableQuantity + :delta
            where inventory.id = :inventoryId
            """)
    void changeAvailableQuantity(@Param("inventoryId") Long inventoryId, @Param("delta") long delta);
}

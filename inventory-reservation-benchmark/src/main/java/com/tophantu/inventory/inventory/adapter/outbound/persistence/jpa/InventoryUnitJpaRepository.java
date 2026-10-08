package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryUnitJpaRepository extends JpaRepository<InventoryUnitJpaEntity, Long> {

    @Modifying
    @Query("delete from InventoryUnitJpaEntity inventoryUnit where inventoryUnit.id in :unitIds")
    void deleteByIdIn(@Param("unitIds") List<Long> unitIds);

    @Query(value = """
            select id
            from inventory_unit
            where inventory_id = :inventoryId
            order by id
            limit :quantity
            for update skip locked
            """, nativeQuery = true)
    List<Long> findIdsForUpdateSkipLocked(@Param("inventoryId") Long inventoryId, @Param("quantity") long quantity);

    long countByInventoryId(Long inventoryId);
}

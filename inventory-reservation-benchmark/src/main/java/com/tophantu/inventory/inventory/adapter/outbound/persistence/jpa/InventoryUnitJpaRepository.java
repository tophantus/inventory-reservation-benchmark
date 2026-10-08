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
}

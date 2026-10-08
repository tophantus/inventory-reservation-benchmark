package com.tophantu.inventory.inventory.adapter.outbound.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
}

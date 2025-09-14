package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.Inventory;
import com.example.mainproject.domain.erp.Item;
import com.example.mainproject.domain.erp.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryRepository extends JpaRepository<Inventory, Long>,
        JpaSpecificationExecutor<Inventory> {
    boolean existsByItemAndWarehouse(Item item, Warehouse warehouse);
    Inventory findByItemAndWarehouse(Item item, Warehouse warehouse);
    Page<Inventory> findByItem_IdAndWarehouse_Id(Long itemId, Long whId, Pageable pageable);
}

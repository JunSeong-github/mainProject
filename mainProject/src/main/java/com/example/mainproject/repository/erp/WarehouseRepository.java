package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsByCode(String code);
}

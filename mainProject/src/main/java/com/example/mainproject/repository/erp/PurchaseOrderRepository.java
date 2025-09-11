package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    boolean existsByPoNo(String poNo);
    Optional<PurchaseOrder> findByPoNo(String poNo);
}

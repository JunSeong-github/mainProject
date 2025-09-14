package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    boolean existsBySoNo(String soNo);
    Optional<SalesOrder> findBySoNo(String soNo);
    Page<SalesOrder> findBySoNoContainingIgnoreCase(String q, Pageable pageable); // ✅ 리스트 검색용
}

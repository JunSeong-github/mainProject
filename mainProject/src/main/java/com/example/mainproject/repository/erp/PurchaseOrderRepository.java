package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;   // ★ 추가 import
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    boolean existsByPoNo(String poNo);
    Optional<PurchaseOrder> findByPoNo(String poNo);
    Page<PurchaseOrder> findByPoNoContainingIgnoreCase(String q, Pageable pageable); // ✅ 리스트 검색용
    // ★ 단건 + lines 컬렉션 함께 로딩
    @EntityGraph(attributePaths = "lines")
    Optional<PurchaseOrder> findById(Long id);
    // 재정의 형태로 써도 되고, 이름을 바꾸고 싶으면 아래처럼 별도 메서드로 만들어도 가능:
    // @EntityGraph(attributePaths = "lines")
    // Optional<PurchaseOrder> findWithLinesById(Long id);
}

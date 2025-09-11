package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByCode(String code);
    Page<Item> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String q1, String q2, Pageable pageable);
}

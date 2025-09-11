package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.POLine;
import com.example.mainproject.domain.erp.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface POLineRepository extends JpaRepository<POLine, Long> {
    List<POLine> findByPo(PurchaseOrder po);
}

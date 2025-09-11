// SOLineRepository.java
package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.SOLine;
import com.example.mainproject.domain.erp.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SOLineRepository extends JpaRepository<SOLine, Long> {
    List<SOLine> findBySo(SalesOrder so);
}

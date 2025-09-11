// ShipmentLineRepository.java
package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.ShipmentLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentLineRepository extends JpaRepository<ShipmentLine, Long> { }

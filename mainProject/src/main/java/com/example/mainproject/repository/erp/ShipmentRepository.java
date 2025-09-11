// ShipmentRepository.java
package com.example.mainproject.repository.erp;

import com.example.mainproject.domain.erp.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> { }

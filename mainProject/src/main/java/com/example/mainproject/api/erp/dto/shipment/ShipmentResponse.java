package com.example.mainproject.api.erp.dto.shipment;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentResponse {
    private Long id;
    private Long soId;
    private LocalDate shipDate;
    private String status;
}

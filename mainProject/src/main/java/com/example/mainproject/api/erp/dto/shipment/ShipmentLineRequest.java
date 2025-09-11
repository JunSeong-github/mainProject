package com.example.mainproject.api.erp.dto.shipment;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentLineRequest {
    @NotNull
    private Long soLineId;   // 주문 상세 ID
    @NotNull
    private BigDecimal shippedQty; // 출고 수량
}

package com.example.mainproject.api.erp.dto.grn;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GRNLineRequest {
    @NotNull
    private Long poLineId;     // 발주 상세 ID
    @NotNull
    private BigDecimal receivedQty; // 실제 입고 수량
}

package com.example.mainproject.api.erp.dto.po;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class POLineRequest {
    @NotNull
    private Long itemId;         // 품목 ID
    @NotNull
    private BigDecimal qty;      // 수량
    @NotNull
    private BigDecimal unitPrice; // 단가
}

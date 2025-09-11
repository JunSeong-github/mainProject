package com.example.mainproject.api.erp.dto.so;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SOLineRequest {
    @NotNull
    private Long itemId;            // ✅ 서비스가 getItemId() 호출

    @NotNull
    private BigDecimal qty;         // ✅ 서비스가 getQty() 호출

    @NotNull
    private BigDecimal unitPrice;   // ✅ 서비스가 getUnitPrice() 호출
}

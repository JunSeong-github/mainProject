package com.example.mainproject.api.erp.dto.po;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class POResponse {
    Long id;
    String poNo;
    String bpName;     // 거래처명
    String status;     // 상태 (DRAFT, APPROVED 등)
    LocalDate orderDate;
    String remark;

    // ★ 응답용 라인
    private List<POLineResp> lines;

    @Getter
    @Builder
    public static class POLineResp {
        private Long id;
        private Long itemId;
        private BigDecimal qty;
        private BigDecimal unitPrice;
        private BigDecimal amount; // 있으면
    }
}

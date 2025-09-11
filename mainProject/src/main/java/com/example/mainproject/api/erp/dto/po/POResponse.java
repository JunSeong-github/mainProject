package com.example.mainproject.api.erp.dto.po;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class POResponse {
    private Long id;          // PO DB PK
    private String poNo;      // 발주 번호
    private String bpName;    // 거래처명
    private String status;    // 상태 (예: OPEN, CLOSED)
    private LocalDate orderDate;
    private String remark;
}

package com.example.mainproject.api.erp.dto.po;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class POResponse {
    Long id;
    String poNo;
    String bpName;     // 거래처명
    String status;     // 상태 (DRAFT, APPROVED 등)
    LocalDate orderDate;
    String remark;
}

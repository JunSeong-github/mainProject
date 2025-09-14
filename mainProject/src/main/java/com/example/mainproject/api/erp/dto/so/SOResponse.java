package com.example.mainproject.api.erp.dto.so;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class SOResponse {
    Long id;
    String soNo;
    String customerName;  // 고객명
    String status;        // 상태 (DRAFT, APPROVED 등)
    LocalDate orderDate;
}

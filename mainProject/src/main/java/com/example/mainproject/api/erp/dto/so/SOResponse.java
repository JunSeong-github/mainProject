package com.example.mainproject.api.erp.dto.so;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SOResponse {
    private Long id;
    private String soNo;
    private String customerName;
    private String status;
    private LocalDate orderDate;
}

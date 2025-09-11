package com.example.mainproject.api.erp.dto.grn;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GRNResponse {
    private Long id;
    private Long poId;
    private LocalDate grnDate;
    private String status;
}

package com.example.mainproject.api.erp.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private Long id;
    private String code;
    private String name;
    private String uom;
    private BigDecimal taxRate;
    private Boolean active;
}

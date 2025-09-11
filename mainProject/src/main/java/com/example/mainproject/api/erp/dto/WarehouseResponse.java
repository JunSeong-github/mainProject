package com.example.mainproject.api.erp.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {
    private Long id;
    private String code;
    private String name;
}

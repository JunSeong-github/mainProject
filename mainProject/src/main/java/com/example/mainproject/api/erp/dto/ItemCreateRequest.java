package com.example.mainproject.api.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCreateRequest {
    @NotBlank @Size(max = 50)
    private String code;

    @NotBlank @Size(max = 200)
    private String name;

    @Size(max = 20)
    private String uom;

    private BigDecimal taxRate;
    private Boolean active;
}

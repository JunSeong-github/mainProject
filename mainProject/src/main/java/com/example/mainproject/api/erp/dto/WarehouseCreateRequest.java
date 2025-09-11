package com.example.mainproject.api.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseCreateRequest {
    @NotBlank @Size(max = 50)
    private String code;

    @NotBlank @Size(max = 200)
    private String name;
}

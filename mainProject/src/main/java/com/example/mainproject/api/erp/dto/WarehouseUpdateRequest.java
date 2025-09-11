package com.example.mainproject.api.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseUpdateRequest {
    @NotBlank @Size(max = 200)
    private String name;
}

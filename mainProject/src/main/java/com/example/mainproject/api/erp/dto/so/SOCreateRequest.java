package com.example.mainproject.api.erp.dto.so;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SOCreateRequest {
    @NotNull
    private String soNo;
    private String customerName;
    @NotNull
    private LocalDate orderDate;

    @NotNull
    private List<SOLineRequest> lines;   // ✅ import java.util.List; 잊지 말기
}

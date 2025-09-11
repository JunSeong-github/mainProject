package com.example.mainproject.api.erp.dto.po;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class POCreateRequest {
    @NotBlank @Size(max=50)
    private String poNo;       // 발주 번호
    private String bpName;     // 거래처명
    @NotNull
    private LocalDate orderDate; // 발주일자
    private String remark;     // 비고
    @NotNull
    private List<POLineRequest> lines; // 발주 상세 항목
}

package com.example.mainproject.api.erp.dto.grn;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GRNCreateRequest {
    @NotNull
    private Long poId;              // 발주 ID

    @NotNull
    private Long warehouseId;       // 입고 창고 ID  ← 추가

    @NotNull
    private LocalDate grnDate;      // 입고일자

    private String remark;          // 비고           ← 추가 (옵션)

    @NotNull
    private List<GRNLineRequest> lines; // 입고 라인
}


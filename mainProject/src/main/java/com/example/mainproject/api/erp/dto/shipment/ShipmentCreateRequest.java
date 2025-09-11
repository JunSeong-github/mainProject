package com.example.mainproject.api.erp.dto.shipment;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentCreateRequest {
    @NotNull
    private Long soId;

    @NotNull
    private Long warehouseId;     // ✅ 서비스가 getWarehouseId() 호출

    @NotNull
    private LocalDate shipDate;

    private String remark;        // ✅ 서비스가 getRemark() 호출

    @NotNull
    private List<ShipmentLineRequest> lines;
}

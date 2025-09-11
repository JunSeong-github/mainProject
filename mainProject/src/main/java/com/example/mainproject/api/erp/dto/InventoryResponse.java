package com.example.mainproject.api.erp.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long id;

    private Long itemId;
    private String itemCode;
    private String itemName;

    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;

    private BigDecimal qtyOnHand;
    private BigDecimal avgCost;
}

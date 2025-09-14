package com.example.mainproject.api.erp.dto.inventory;

import java.math.BigDecimal;

public record InventoryResponse(
        Long id,
        String itemCode,
        String itemName,
        Long warehouseId,
        String warehouseName,
        BigDecimal qtyOnHand,
        BigDecimal avgCost
) { }

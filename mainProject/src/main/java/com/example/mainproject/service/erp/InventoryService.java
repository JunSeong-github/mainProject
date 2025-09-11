package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.InventoryResponse;
import com.example.mainproject.domain.erp.Inventory;
import com.example.mainproject.repository.erp.InventoryRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository repo;

    public InventoryService(InventoryRepository repo) {
        this.repo = repo;
    }

    public Page<InventoryResponse> list(Long itemId, Long whId, Pageable pageable) {
        Page<Inventory> page;
        if (itemId != null && whId != null) {
            page = repo.findByItem_IdAndWarehouse_Id(itemId, whId, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        return page.map(e -> InventoryResponse.builder()
                .id(e.getId())
                .itemId(e.getItem().getId())
                .itemCode(e.getItem().getCode())
                .itemName(e.getItem().getName())
                .warehouseId(e.getWarehouse().getId())
                .warehouseCode(e.getWarehouse().getCode())
                .warehouseName(e.getWarehouse().getName())
                .qtyOnHand(e.getQtyOnHand())
                .avgCost(e.getAvgCost())
                .build()
        );
    }
}

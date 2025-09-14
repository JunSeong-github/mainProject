package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.common.PageResp;
import com.example.mainproject.api.erp.dto.inventory.InventoryResponse;
import com.example.mainproject.domain.erp.Inventory;
import com.example.mainproject.repository.erp.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository invRepo;

    @GetMapping
    public PageResp<InventoryResponse> list(
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) Long warehouseId,
            Pageable pageable
    ) {
        Specification<Inventory> spec = Specification.where((root, q, cb) -> cb.conjunction());
        if (itemCode != null && !itemCode.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("item").get("code")),
                            "%" + itemCode.toLowerCase() + "%"));
        }
        if (warehouseId != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("warehouse").get("id"), warehouseId));
        }

        Page<Inventory> page = invRepo.findAll(spec, pageable);
        List<InventoryResponse> content = page.getContent().stream()
                .map(inv -> new InventoryResponse(
                        inv.getId(),
                        inv.getItem().getCode(),
                        inv.getItem().getName(),
                        inv.getWarehouse().getId(),
                        inv.getWarehouse().getName(),
                        inv.getQtyOnHand(),
                        inv.getAvgCost()
                ))
                .toList();

        return new PageResp<>(content, page.getTotalElements(), page.getTotalPages(),
                page.getNumber(), page.getSize());
    }
}

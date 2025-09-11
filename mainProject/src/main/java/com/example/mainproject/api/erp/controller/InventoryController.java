package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.InventoryResponse;
import com.example.mainproject.service.erp.InventoryService;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService svc;
    public InventoryController(InventoryService svc) { this.svc = svc; }

    @GetMapping
    public Page<InventoryResponse> list(@RequestParam(required=false) Long itemId,
                                        @RequestParam(required=false) Long warehouseId,
                                        @RequestParam(defaultValue="0") int page,
                                        @RequestParam(defaultValue="20") int size) {
        return svc.list(itemId, warehouseId, PageRequest.of(page, size, Sort.by("id").descending()));
    }
}

package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.po.*;
import com.example.mainproject.service.erp.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService svc;
    public PurchaseController(PurchaseService svc) { this.svc = svc; }

    @PostMapping("/orders")
    public POResponse create(@RequestBody @Valid POCreateRequest req) {
        return svc.createPO(req);
    }

    @PutMapping("/orders/{poId}") // 또는 @PatchMapping
    public POResponse update(@PathVariable Long poId, @RequestBody @Valid POCreateRequest req) {
        return svc.updatePO(poId, req); // 서비스에 update 로직 추가
    }

    @PostMapping("/orders/{poId}/approve")
    public POResponse approve(@PathVariable Long poId) {
        return svc.approvePO(poId);
    }
}

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

    @PostMapping("/orders/{poId}/approve")
    public POResponse approve(@PathVariable Long poId) {
        return svc.approvePO(poId);
    }
}

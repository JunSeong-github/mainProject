package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.so.*;
import com.example.mainproject.service.erp.SalesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService svc;
    public SalesController(SalesService svc) { this.svc = svc; }

    @PostMapping("/orders")
    public SOResponse create(@RequestBody @Valid SOCreateRequest req) {
        return svc.createSO(req);
    }

    @PostMapping("/orders/{soId}/approve")
    public SOResponse approve(@PathVariable Long soId) {
        return svc.approveSO(soId);
    }
}

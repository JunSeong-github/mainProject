package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.common.PageResp;
import com.example.mainproject.api.erp.dto.po.POCreateRequest;
import com.example.mainproject.api.erp.dto.po.POResponse;
import com.example.mainproject.service.erp.PurchaseQueryService;
import com.example.mainproject.service.erp.PurchaseService; // 네가 만든 커맨드 서비스(생성/승인)
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseQueryController {

    private final PurchaseQueryService query;
    private final PurchaseService command;

    @GetMapping("/orders")
    public PageResp<POResponse> list(@RequestParam(required = false) String q, Pageable pageable) {
        return query.list(q, pageable);
    }

    @GetMapping("/orders/{id}")
    public POResponse get(@PathVariable Long id) {
        return query.get(id);
    }

    @PostMapping("/orders")
    public POResponse create(@Valid @RequestBody POCreateRequest req) {
        return command.createPO(req);
    }

    @PostMapping("/orders/{id}/approve")
    public POResponse approve(@PathVariable Long id) {
        return command.approvePO(id);
    }
}

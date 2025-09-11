package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.common.PageResp;
import com.example.mainproject.api.erp.dto.so.SOCreateRequest;
import com.example.mainproject.api.erp.dto.so.SOResponse;
import com.example.mainproject.service.erp.SalesQueryService;
import com.example.mainproject.service.erp.SalesService; // 네가 만든 커맨드 서비스(생성/승인)
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesQueryController {

    private final SalesQueryService query;
    private final SalesService command;

    @GetMapping("/orders")
    public PageResp<SOResponse> list(@RequestParam(required = false) String q, Pageable pageable) {
        return query.list(q, pageable);
    }

    @GetMapping("/orders/{id}")
    public SOResponse get(@PathVariable Long id) {
        return query.get(id);
    }

    @PostMapping("/orders")
    public SOResponse create(@Valid @RequestBody SOCreateRequest req) {
        return command.createSO(req);
    }

    @PostMapping("/orders/{id}/approve")
    public SOResponse approve(@PathVariable Long id) {
        return command.approveSO(id);
    }
}

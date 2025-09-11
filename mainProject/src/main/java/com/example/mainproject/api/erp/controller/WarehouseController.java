package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.*;
import com.example.mainproject.service.erp.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService svc;
    public WarehouseController(WarehouseService svc) { this.svc = svc; }

    @GetMapping
    public Page<WarehouseResponse> list(@RequestParam(defaultValue="0") int page,
                                        @RequestParam(defaultValue="20") int size) {
        return svc.list(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @GetMapping("/{id}")
    public WarehouseResponse get(@PathVariable Long id) { return svc.get(id); }

    @PostMapping
    public WarehouseResponse create(@RequestBody @Valid WarehouseCreateRequest req) { return svc.create(req); }

    @PatchMapping("/{id}")
    public WarehouseResponse update(@PathVariable Long id, @RequestBody @Valid WarehouseUpdateRequest req) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { svc.delete(id); }
}

package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.*;
import com.example.mainproject.service.erp.ItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService svc;
    public ItemController(ItemService svc) { this.svc = svc; }

    @GetMapping
    public Page<ItemResponse> list(@RequestParam(required=false) String q,
                                   @RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="20") int size) {
        return svc.list(q, PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @GetMapping("/{id}")
    public ItemResponse get(@PathVariable Long id) { return svc.get(id); }

    @PostMapping
    public ItemResponse create(@RequestBody @Valid ItemCreateRequest req) { return svc.create(req); }

    @PatchMapping("/{id}")
    public ItemResponse update(@PathVariable Long id, @RequestBody @Valid ItemUpdateRequest req) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { svc.delete(id); }
}

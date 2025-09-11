package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.*;
import com.example.mainproject.domain.erp.Item;
import com.example.mainproject.repository.erp.ItemRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class ItemService {

    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public ItemResponse create(ItemCreateRequest req) {
        if (repo.existsByCode(req.getCode())) {
            throw new IllegalArgumentException("중복 코드: " + req.getCode());
        }
        Item e = Item.builder()
                .code(req.getCode())
                .name(req.getName())
                .uom(req.getUom())
                .taxRate(req.getTaxRate() == null ? BigDecimal.ZERO : req.getTaxRate())
                .active(req.getActive() == null ? Boolean.TRUE : req.getActive())
                .build();

        e = repo.save(e);
        return toRes(e);
    }

    @Transactional(readOnly = true)
    public Page<ItemResponse> list(String q, Pageable pageable) {
        Page<Item> page = (q == null || q.isBlank())
                ? repo.findAll(pageable)
                : repo.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(q, q, pageable);
        return page.map(this::toRes);
    }

    @Transactional(readOnly = true)
    public ItemResponse get(Long id) {
        Item e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return toRes(e);
    }

    public ItemResponse update(Long id, ItemUpdateRequest req) {
        Item e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        e.setName(req.getName());
        e.setUom(req.getUom());
        e.setTaxRate(req.getTaxRate() == null ? BigDecimal.ZERO : req.getTaxRate());
        e.setActive(req.getActive() == null ? Boolean.TRUE : req.getActive());
        // JPA dirty checking으로 save 불필요
        return toRes(e);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private ItemResponse toRes(Item e) {
        return ItemResponse.builder()
                .id(e.getId())
                .code(e.getCode())
                .name(e.getName())
                .uom(e.getUom())
                .taxRate(e.getTaxRate())
                .active(e.getActive())
                .build();
    }
}

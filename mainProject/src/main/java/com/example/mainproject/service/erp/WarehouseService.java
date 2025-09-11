package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.*;
import com.example.mainproject.domain.erp.Warehouse;
import com.example.mainproject.repository.erp.WarehouseRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WarehouseService {

    private final WarehouseRepository repo;

    public WarehouseService(WarehouseRepository repo) {
        this.repo = repo;
    }

    public WarehouseResponse create(WarehouseCreateRequest req) {
        if (repo.existsByCode(req.getCode())) {
            throw new IllegalArgumentException("중복 코드: " + req.getCode());
        }
        Warehouse e = Warehouse.builder()
                .code(req.getCode())
                .name(req.getName())
                .build();
        e = repo.save(e);
        return toRes(e);
    }

    @Transactional(readOnly = true)
    public Page<WarehouseResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toRes);
    }

    @Transactional(readOnly = true)
    public WarehouseResponse get(Long id) {
        Warehouse e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return toRes(e);
    }

    public WarehouseResponse update(Long id, WarehouseUpdateRequest req) {
        Warehouse e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        e.setName(req.getName());
        return toRes(e);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private WarehouseResponse toRes(Warehouse e) {
        return WarehouseResponse.builder()
                .id(e.getId())
                .code(e.getCode())
                .name(e.getName())
                .build();
    }
}

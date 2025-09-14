package com.example.mainproject.service.erp;

import com.example.mainproject.api.common.PageResp;
import com.example.mainproject.api.erp.dto.so.SOResponse;
import com.example.mainproject.domain.erp.SalesOrder;
import com.example.mainproject.repository.erp.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesQueryService {

    private final SalesOrderRepository soRepo;

    public PageResp<SOResponse> list(String q, Pageable pageable) {
        Page<SalesOrder> page = (q == null || q.isBlank())
                ? soRepo.findAll(pageable)
                : soRepo.findBySoNoContainingIgnoreCase(q, pageable);

        List<SOResponse> content = page.getContent().stream()
                .map(so -> SOResponse.builder()
                        .id(so.getId())
                        .soNo(so.getSoNo())
                        .customerName(so.getBpName())
                        .status(so.getStatus().name())
                        .orderDate(so.getOrderDate())
                        .build())
                .toList();

        return new PageResp<>(content, page.getTotalElements(), page.getTotalPages(),
                page.getNumber(), page.getSize());
    }

    public SOResponse get(Long id) {
        SalesOrder so = soRepo.findById(id).orElseThrow();
        return SOResponse.builder()
                .id(so.getId())
                .soNo(so.getSoNo())
                .customerName(so.getBpName())
                .status(so.getStatus().name())
                .orderDate(so.getOrderDate())
                .build();
    }
}

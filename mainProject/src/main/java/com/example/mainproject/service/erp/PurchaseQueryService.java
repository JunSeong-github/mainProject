package com.example.mainproject.service.erp;

import com.example.mainproject.api.common.PageResp;
import com.example.mainproject.api.erp.dto.po.POResponse;
import com.example.mainproject.domain.erp.PurchaseOrder;
import com.example.mainproject.repository.erp.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseQueryService {

    private final PurchaseOrderRepository poRepo;

    public PageResp<POResponse> list(String q, Pageable pageable) {
        Page<PurchaseOrder> page = (q == null || q.isBlank())
                ? poRepo.findAll(pageable)
                : poRepo.findByPoNoContainingIgnoreCase(q, pageable);

        List<POResponse> content = page.getContent().stream()
                .map(po -> POResponse.builder()
                        .id(po.getId())
                        .poNo(po.getPoNo())
                        .bpName(po.getBpName())
                        .status(po.getStatus().name())
                        .orderDate(po.getOrderDate())
                        .remark(po.getRemark())
                        .build())
                .toList();

        return new PageResp<>(content, page.getTotalElements(), page.getTotalPages(),
                page.getNumber(), page.getSize());
    }

    public POResponse get(Long id) {
        PurchaseOrder po = poRepo.findById(id).orElseThrow();
        return POResponse.builder()
                .id(po.getId())
                .poNo(po.getPoNo())
                .bpName(po.getBpName())
                .status(po.getStatus().name())
                .orderDate(po.getOrderDate())
                .remark(po.getRemark())
                .build();
    }
}

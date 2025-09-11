package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.so.*;
import com.example.mainproject.domain.erp.*;
import com.example.mainproject.repository.erp.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class SalesService {

    private final SalesOrderRepository soRepo;
    private final SOLineRepository soLineRepo;
    private final ItemRepository itemRepo;

    public SalesService(SalesOrderRepository soRepo, SOLineRepository soLineRepo, ItemRepository itemRepo) {
        this.soRepo = soRepo;
        this.soLineRepo = soLineRepo;
        this.itemRepo = itemRepo;
    }

    public SOResponse createSO(SOCreateRequest req) {
        if (soRepo.existsBySoNo(req.getSoNo())) {
            throw new IllegalArgumentException("중복 주문번호: " + req.getSoNo());
        }

        SalesOrder so = SalesOrder.builder()
                .soNo(req.getSoNo())
                .bpName(req.getCustomerName())
                .status(SalesOrder.SOStatus.DRAFT)
                .orderDate(req.getOrderDate())
                .remark(null)
                .build();
        so = soRepo.save(so);

        for (SOLineRequest lr : req.getLines()) {
            Item item = itemRepo.findById(lr.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found: " + lr.getItemId()));
            BigDecimal amount = lr.getQty().multiply(lr.getUnitPrice());

            SOLine line = SOLine.builder()
                    .so(so)
                    .item(item)
                    .qty(lr.getQty())
                    .unitPrice(lr.getUnitPrice())
                    .amount(amount)
                    .build();
            soLineRepo.save(line);
        }

        return SOResponse.builder()
                .id(so.getId())
                .soNo(so.getSoNo())
                .customerName(so.getBpName())
                .status(so.getStatus().name())
                .orderDate(so.getOrderDate())
                .build();
    }

    public SOResponse approveSO(Long soId) {
        SalesOrder so = soRepo.findById(soId)
                .orElseThrow(() -> new IllegalArgumentException("SO not found: " + soId));
        so.setStatus(SalesOrder.SOStatus.APPROVED);
        return SOResponse.builder()
                .id(so.getId()).soNo(so.getSoNo()).customerName(so.getBpName())
                .status(so.getStatus().name()).orderDate(so.getOrderDate())
                .build();
    }
}

package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.po.*;
import com.example.mainproject.domain.erp.*;
import com.example.mainproject.repository.erp.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseOrderRepository poRepo;
    private final POLineRepository poLineRepo;
    private final ItemRepository itemRepo;

    public PurchaseService(PurchaseOrderRepository poRepo, POLineRepository poLineRepo, ItemRepository itemRepo) {
        this.poRepo = poRepo;
        this.poLineRepo = poLineRepo;
        this.itemRepo = itemRepo;
    }

    public POResponse createPO(POCreateRequest req) {
        if (poRepo.existsByPoNo(req.getPoNo())) {
            throw new IllegalArgumentException("중복 발주번호: " + req.getPoNo());
        }

        PurchaseOrder po = PurchaseOrder.builder()
                .poNo(req.getPoNo())
                .bpName(req.getBpName())
                .status(PurchaseOrder.POStatus.DRAFT)
                .orderDate(req.getOrderDate())
                .remark(req.getRemark())
                .build();
        po = poRepo.save(po);

        for (POLineRequest lr : req.getLines()) {
            Item item = itemRepo.findById(lr.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found: " + lr.getItemId()));
            BigDecimal amount = lr.getQty().multiply(lr.getUnitPrice());

            POLine line = POLine.builder()
                    .po(po)
                    .item(item)
                    .qty(lr.getQty())
                    .unitPrice(lr.getUnitPrice())
                    .amount(amount)
                    .build();
            poLineRepo.save(line);
        }

        return POResponse.builder()
                .id(po.getId())
                .poNo(po.getPoNo())
                .bpName(po.getBpName())
                .status(po.getStatus().name())
                .orderDate(po.getOrderDate())
                .remark(po.getRemark())
                .build();
    }

    public POResponse approvePO(Long poId) {
        PurchaseOrder po = poRepo.findById(poId)
                .orElseThrow(() -> new IllegalArgumentException("PO not found: " + poId));
        po.setStatus(PurchaseOrder.POStatus.APPROVED);
        return POResponse.builder()
                .id(po.getId()).poNo(po.getPoNo()).bpName(po.getBpName())
                .status(po.getStatus().name()).orderDate(po.getOrderDate()).remark(po.getRemark())
                .build();
    }
}

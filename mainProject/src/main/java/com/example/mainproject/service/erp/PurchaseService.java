package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.po.*;
import com.example.mainproject.domain.erp.*;
import com.example.mainproject.repository.erp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {

    private final PurchaseOrderRepository poRepo;
    private final ItemRepository itemRepo; // ★ 필요

    public POResponse createPO(POCreateRequest req) {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNo(req.getPoNo());
        po.setBpName(req.getBpName());
        po.setOrderDate(req.getOrderDate());
        po.setRemark(req.getRemark());
        po.setStatus(PurchaseOrder.POStatus.DRAFT);

        applyLines(po, req.getLines());

        poRepo.save(po); // cascade로 라인 insert
        return toResponse(po);
    }

    public POResponse updatePO(Long poId, POCreateRequest req) {
        PurchaseOrder po = poRepo.findById(poId)
                .orElseThrow(() -> new IllegalArgumentException("PO not found: " + poId));

        if (po.getStatus() != PurchaseOrder.POStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT can be updated.");
        }

        po.setPoNo(req.getPoNo());
        po.setBpName(req.getBpName());
        po.setOrderDate(req.getOrderDate());
        po.setRemark(req.getRemark());

        // 교체 전략
        po.getLines().clear();
        applyLines(po, req.getLines());

        return toResponse(po);
    }

    private void applyLines(PurchaseOrder po, List<POLineRequest> reqLines) {
        if (reqLines == null || reqLines.isEmpty()) return;

        for (POLineRequest lr : reqLines) {
            if (lr.getItemId() == null) {
                throw new IllegalArgumentException("itemId is required");
            }
            if (lr.getQty() == null || lr.getUnitPrice() == null) {
                throw new IllegalArgumentException("qty/unitPrice is required");
            }

            // ★ 연관만 세팅: 쿼리 안 나가는 프록시
            Item itemRef = itemRepo.getReferenceById(lr.getItemId());

            POLine line = new POLine();
            line.setPo(po);

            line.setItem(itemRef);
            line.setQty(lr.getQty());
            line.setUnitPrice(lr.getUnitPrice());
            line.setAmount(lr.getQty().multiply(lr.getUnitPrice())); // NOT NULL이면 필수

            po.getLines().add(line);
        }
    }

    @Transactional
    public POResponse approvePO(Long poId) {
        PurchaseOrder po = poRepo.findById(poId)
                .orElseThrow(() -> new IllegalArgumentException("PO not found: " + poId));

        // 팀 규칙: DRAFT만 승인 가능
        if (po.getStatus() != PurchaseOrder.POStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT can be approved.");
        }

        // (선택) 라인 없는 발주는 승인 불가하게 하려면 활성화
        // if (po.getLines() == null || po.getLines().isEmpty()) {
        //     throw new IllegalStateException("Cannot approve without any lines.");
        // }

        // 상태 전이
        po.setStatus(PurchaseOrder.POStatus.APPROVED);

        // (선택) 승인일/승인자 필드가 있다면 여기서 세팅
        // po.setApprovedAt(LocalDateTime.now());
        // po.setApprovedBy(currentUserId);

        // flush는 @Transactional로 커밋 시점에 자동 반영
        // 응답은 기존 방식에 맞춰 반환
        return toResponse(po);  // ← 너희 서비스에 toResponse(po) 유틸이 있으면 이대로
    }

    private POResponse toResponse(PurchaseOrder po) {
        // 너가 쓰는 POResponse 매핑 사용. (lines 포함하면 프런트 상세에 바로 반영)
        // ... 기존 코드 유지 ...
        return POResponse.builder()
                .id(po.getId())
                .poNo(po.getPoNo())
                .bpName(po.getBpName())
                .status(po.getStatus().name())
                .orderDate(po.getOrderDate())
                .remark(po.getRemark())
                .lines(
                        po.getLines().stream().map(l -> POResponse.POLineResp.builder()
                                .id(l.getId())
                                .itemId(l.getItem().getId())  // 응답에선 id로 내려줌
                                .qty(l.getQty())
                                .unitPrice(l.getUnitPrice())
                                .amount(l.getAmount())
                                .build()
                        ).toList()
                )
                .build();
    }
}


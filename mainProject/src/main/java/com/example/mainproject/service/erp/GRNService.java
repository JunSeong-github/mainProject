package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.grn.*;
import com.example.mainproject.domain.erp.*;
import com.example.mainproject.repository.erp.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class GRNService {

    private final GRNRepository grnRepo;
    private final GRNLineRepository grnLineRepo;
    private final PurchaseOrderRepository poRepo;
    private final POLineRepository poLineRepo;
    private final WarehouseRepository whRepo;
    private final InventoryRepository invRepo;

    public GRNService(GRNRepository grnRepo, GRNLineRepository grnLineRepo,
                      PurchaseOrderRepository poRepo, POLineRepository poLineRepo,
                      WarehouseRepository whRepo, InventoryRepository invRepo) {
        this.grnRepo = grnRepo;
        this.grnLineRepo = grnLineRepo;
        this.poRepo = poRepo;
        this.poLineRepo = poLineRepo;
        this.whRepo = whRepo;
        this.invRepo = invRepo;
    }

    public GRNResponse receive(GRNCreateRequest req) {
        PurchaseOrder po = poRepo.findById(req.getPoId())
                .orElseThrow(() -> new IllegalArgumentException("PO not found: " + req.getPoId()));
        if (po.getStatus() != PurchaseOrder.POStatus.APPROVED) {
            throw new IllegalStateException("PO not approved");
        }

        // 이번 DTO는 가격 필드가 없으므로, 평균단가 갱신에 PO 라인의 unitPrice를 사용
        Warehouse wh = whRepo.findById(req.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + req.getWarehouseId()));

        GRN grn = GRN.builder()
                .po(po)
                .warehouse(wh)
                .receivedAt(LocalDateTime.of(req.getGrnDate(), java.time.LocalTime.of(12, 0)))
                .remark(req.getRemark())
                .build();
        grn = grnRepo.save(grn);

        for (GRNLineRequest lr : req.getLines()) {
            POLine poLine = poLineRepo.findById(lr.getPoLineId())
                    .orElseThrow(() -> new IllegalArgumentException("PO Line not found: " + lr.getPoLineId()));

            // GRN 라인 저장
            GRNLine gl = GRNLine.builder()
                    .grn(grn)
                    .poLine(poLine)
                    .recvQty(lr.getReceivedQty())
                    .recvPrice(poLine.getUnitPrice()) // 단가 = 발주 단가
                    .build();
            grnLineRepo.save(gl);

            // Inventory upsert
            Inventory inv = invRepo.findByItemAndWarehouse(poLine.getItem(), wh);
            if (inv == null) {
                inv = Inventory.builder()
                        .item(poLine.getItem())
                        .warehouse(wh)
                        .qtyOnHand(BigDecimal.ZERO)
                        .avgCost(BigDecimal.ZERO)
                        .build();
            }

            // 가중평균단가 갱신
            BigDecimal oldQty = inv.getQtyOnHand();
            BigDecimal oldAvg = inv.getAvgCost();
            BigDecimal rQty   = lr.getReceivedQty();
            BigDecimal rPrice = poLine.getUnitPrice();

            BigDecimal newQty = oldQty.add(rQty);
            BigDecimal newAvg = oldQty.compareTo(BigDecimal.ZERO) == 0
                    ? rPrice
                    : (oldQty.multiply(oldAvg).add(rQty.multiply(rPrice)))
                    .divide(newQty, 6, java.math.RoundingMode.HALF_UP);

            inv.setQtyOnHand(newQty);
            inv.setAvgCost(newAvg);
            invRepo.save(inv);
        }

        return GRNResponse.builder()
                .id(grn.getId())
                .poId(po.getId())
                .grnDate(req.getGrnDate())
                .status("RECEIVED")
                .build();
    }
}

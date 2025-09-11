package com.example.mainproject.service.erp;

import com.example.mainproject.api.erp.dto.shipment.*;
import com.example.mainproject.domain.erp.*;
import com.example.mainproject.repository.erp.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Service
@Transactional
public class ShipmentService {

    private final ShipmentRepository shipRepo;
    private final ShipmentLineRepository shipLineRepo;
    private final SalesOrderRepository soRepo;
    private final SOLineRepository soLineRepo;
    private final WarehouseRepository whRepo;
    private final InventoryRepository invRepo;

    public ShipmentService(ShipmentRepository shipRepo, ShipmentLineRepository shipLineRepo,
                           SalesOrderRepository soRepo, SOLineRepository soLineRepo,
                           WarehouseRepository whRepo, InventoryRepository invRepo) {
        this.shipRepo = shipRepo;
        this.shipLineRepo = shipLineRepo;
        this.soRepo = soRepo;
        this.soLineRepo = soLineRepo;
        this.whRepo = whRepo;
        this.invRepo = invRepo;
    }

    public ShipmentResponse ship(ShipmentCreateRequest req) {
        SalesOrder so = soRepo.findById(req.getSoId())
                .orElseThrow(() -> new IllegalArgumentException("SO not found: " + req.getSoId()));
        if (so.getStatus() != SalesOrder.SOStatus.APPROVED) {
            throw new IllegalStateException("SO not approved");
        }

        Warehouse wh = whRepo.findById(req.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + req.getWarehouseId()));

        Shipment sh = Shipment.builder()
                .so(so)
                .warehouse(wh)
                .shippedAt(LocalDateTime.of(req.getShipDate(), java.time.LocalTime.of(12, 0)))
                .remark(req.getRemark())
                .build();
        sh = shipRepo.save(sh);

        for (ShipmentLineRequest lr : req.getLines()) {
            SOLine soLine = soLineRepo.findById(lr.getSoLineId())
                    .orElseThrow(() -> new IllegalArgumentException("SO Line not found: " + lr.getSoLineId()));

            // 라인 저장
            ShipmentLine sl = ShipmentLine.builder()
                    .shipment(sh)
                    .soLine(soLine)
                    .shipQty(lr.getShippedQty())
                    .build();
            shipLineRepo.save(sl);

            // 재고 차감
            Inventory inv = invRepo.findByItemAndWarehouse(soLine.getItem(), wh);
            if (inv == null) throw new IllegalStateException("재고 없음: item=" + soLine.getItem().getCode());

            BigDecimal oldQty = inv.getQtyOnHand();
            if (oldQty.compareTo(lr.getShippedQty()) < 0) {
                throw new IllegalStateException("재고 부족: onHand=" + oldQty + ", need=" + lr.getShippedQty());
            }
            inv.setQtyOnHand(oldQty.subtract(lr.getShippedQty())); // 평균단가는 유지
            invRepo.save(inv);
        }

        so.setStatus(SalesOrder.SOStatus.SHIPPED);

        return ShipmentResponse.builder()
                .id(sh.getId())
                .soId(so.getId())
                .shipDate(req.getShipDate())
                .status("SHIPPED")
                .build();
    }
}

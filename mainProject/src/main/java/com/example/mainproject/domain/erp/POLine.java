package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name="po_line")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class POLine {

    // ★ 핵심: 부모 필드명 'po'로 선언 (mappedBy와 동일해야 함)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)  // 컬럼명은 DB에 맞게
    private PurchaseOrder po;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 읽기/조인 전용 (같은 컬럼을 공유하므로 반드시 write 금지)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal qty;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal unitPrice;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal amount; // qty * unitPrice
}

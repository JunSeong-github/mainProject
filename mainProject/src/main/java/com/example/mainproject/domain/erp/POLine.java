package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name="po_line")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class POLine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="po_id")
    private PurchaseOrder po;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="item_id")
    private Item item;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal qty;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal unitPrice;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal amount; // qty * unitPrice
}

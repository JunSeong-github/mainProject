package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="grn")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GRN {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="po_id")
    private PurchaseOrder po;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;

    @Column(nullable=false)
    private LocalDateTime receivedAt;

    @Column(length=500)
    private String remark;
}

package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name="purchase_order")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PurchaseOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=50)
    private String poNo;                  // 발주번호

    @Column(length=200)
    private String bpName;                // 공급사명(간단화)

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private POStatus status;              // DRAFT, APPROVED, CLOSED

    @Column(nullable=false)
    private LocalDate orderDate;

    @Column(length=500)
    private String remark;

    public enum POStatus { DRAFT, APPROVED, CLOSED }
}

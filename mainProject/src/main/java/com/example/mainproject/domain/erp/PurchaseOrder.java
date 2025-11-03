package com.example.mainproject.domain.erp;

import com.example.mainproject.api.erp.dto.po.POLineRequest;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

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
    private POStatus status = POStatus.DRAFT;              // DRAFT, APPROVED, CLOSED

    @Column(nullable=false)
    private LocalDate orderDate;

    @Column(length=500)
    private String remark;

    // ★ mappedBy 값 "po" == POLine의 부모 필드명과 동일해야 함
    @OneToMany(mappedBy = "po", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.Builder.Default
    private List<POLine> lines = new ArrayList<>();

    public enum POStatus { DRAFT, APPROVED, CLOSED }
}

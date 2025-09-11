// SalesOrder.java
package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="sales_order")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SalesOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=50)
    private String soNo;
    private String bpName;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20)
    private SOStatus status;
    private LocalDate orderDate;
    public enum SOStatus { DRAFT, APPROVED, SHIPPED, CLOSED }
    private String remark;
}

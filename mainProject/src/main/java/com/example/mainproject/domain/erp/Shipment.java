// Shipment.java
package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="shipment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Shipment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="so_id")
    private SalesOrder so;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;
    private LocalDateTime shippedAt;
    private String remark;
}

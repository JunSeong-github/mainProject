// ShipmentLine.java
package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name="shipment_line")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="shipment_id")
    private Shipment shipment;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="so_line_id")
    private SOLine soLine;
    private BigDecimal shipQty;
}

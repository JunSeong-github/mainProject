// SOLine.java
package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name="so_line")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SOLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="so_id")
    private SalesOrder so;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="item_id")
    private Item item;
    private BigDecimal qty;
    private BigDecimal unitPrice;
    private BigDecimal amount;
}

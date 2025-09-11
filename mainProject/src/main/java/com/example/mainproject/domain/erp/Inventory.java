package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "inventory",
        uniqueConstraints = @UniqueConstraint(name="uq_inventory_item_wh", columnNames = {"item_id","warehouse_id"})
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;

    @Column(precision=18, scale=6)
    private BigDecimal qtyOnHand = BigDecimal.ZERO;

    @Column(precision=18, scale=6)
    private BigDecimal avgCost = BigDecimal.ZERO;

    @Version
    private Long version; // 낙관적 락
}

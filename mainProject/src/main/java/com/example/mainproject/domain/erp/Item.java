package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=50)
    private String code;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=20)
    private String uom;

    @Column(precision=5, scale=2)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Column(nullable=false)
    private Boolean active = true;
}

package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name="grn_line")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GRNLine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="grn_id")
    private GRN grn;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="po_line_id")
    private POLine poLine;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal recvQty;

    @Column(precision=18, scale=6, nullable=false)
    private BigDecimal recvPrice; // 입고단가
}

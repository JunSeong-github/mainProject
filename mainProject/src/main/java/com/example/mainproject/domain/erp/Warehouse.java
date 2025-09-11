package com.example.mainproject.domain.erp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouse")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=50)
    private String code;

    @Column(nullable=false, length=200)
    private String name;
}

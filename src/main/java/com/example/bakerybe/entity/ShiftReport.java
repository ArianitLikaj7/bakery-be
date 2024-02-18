package com.example.bakerybe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shift_report")
@Entity
public class ShiftReport extends BaseEntity{

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "bakery_id", insertable = false, updatable = false)
    private Bakery bakery;

    @Column(name = "bakery_id")
    private Long bakeryId;

    @Column(name = "produced_quantity")
    private Integer producedQuantity;

    @Column(name = "left_quantity")
    private Integer leftQuantity;

    @Column(name = "daily_earnings")
    private BigDecimal dailyEarnings;
}

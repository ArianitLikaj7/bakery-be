package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shift_report_products")
@Entity
public class ShiftReportProduct extends BaseEntity {

    @Column(name = "shift_report_id", nullable = false)
    private Long shiftReportId;

    @ManyToOne
    @JoinColumn(name = "shift_report_id", insertable = false, updatable = false)
    @JsonBackReference
    private ShiftReport shiftReport;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "produced_quantity", nullable = false)
    private Integer producedQuantity = 0;

    @Column(name = "left_quantity", nullable = false)
    private Integer leftQuantity = 0;

    @Column(name = "daily_earnings", nullable = false)
    private BigDecimal dailyEarnings = BigDecimal.ZERO;
}

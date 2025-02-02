package com.example.bakerybe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "shift_report_product_summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftReportProductSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Integer producedQuantity;
    private Integer leftQuantity;
    private BigDecimal productPrice;
    private BigDecimal productProfit;

    @ManyToOne
    @JoinColumn(name = "shift_report_summary_id", nullable = false)
    private ShiftReportSummary shiftReportSummary;
}

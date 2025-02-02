package com.example.bakerybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftReportProductSummaryDto {
    private String productName;
    private int producedQuantity;
    private int leftQuantity;
    private BigDecimal price;
    private BigDecimal profit;
}

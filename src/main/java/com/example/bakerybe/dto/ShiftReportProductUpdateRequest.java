package com.example.bakerybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftReportProductUpdateRequest {
    private Long productId;
    private int producedQuantity;
    private int leftQuantity;
    private BigDecimal dailyEarnings;
}
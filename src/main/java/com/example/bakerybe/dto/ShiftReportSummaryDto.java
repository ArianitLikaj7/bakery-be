package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Shift;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftReportSummaryDto {
    private LocalDate reportDate;
    private Shift shift;
    private List<ShiftReportProductSummaryDto> productSummaries;
    private BigDecimal totalDailyEarnings;
}

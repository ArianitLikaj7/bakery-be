package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Shift;

public record ShiftStartProductionReportRequest(
        Shift shift,
        Long productId,
        Integer quantityProduced
) {
}

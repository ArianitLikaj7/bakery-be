package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Shift;

public record ShiftEndInventoryReportRequest(
        Long shiftReportId,
        Integer quantityLeft
) {
}

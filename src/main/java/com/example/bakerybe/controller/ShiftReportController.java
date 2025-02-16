package com.example.bakerybe.controller;

import com.example.bakerybe.dto.ShiftReportProductUpdateRequest;
import com.example.bakerybe.dto.ShiftReportResponse;
import com.example.bakerybe.dto.ShiftReportSummaryDto;
import com.example.bakerybe.entity.Product;
import com.example.bakerybe.entity.Shift;
import com.example.bakerybe.entity.ShiftReport;
import com.example.bakerybe.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shift-reports")
@RequiredArgsConstructor
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @PostMapping("/create")
    public ResponseEntity<ShiftReportResponse> createShift(@RequestParam Long bakeryId, @RequestParam Shift shift) {
        return ResponseEntity.ok(shiftReportService.createShift(bakeryId, shift));
    }

    @PutMapping("/save/{shiftReportId}")
    public ResponseEntity<Long> saveShift(
            @PathVariable Long shiftReportId,
            @RequestBody List<ShiftReportProductUpdateRequest> updates) {
        return ResponseEntity.ok(shiftReportService.saveShift(shiftReportId, updates));
    }

    @GetMapping("/generate/{shiftReportId}")
    public ResponseEntity<ShiftReportSummaryDto> generateShiftReport(@PathVariable Long shiftReportId) {
        return ResponseEntity.ok(shiftReportService.generateShiftReport(shiftReportId));
    }
}

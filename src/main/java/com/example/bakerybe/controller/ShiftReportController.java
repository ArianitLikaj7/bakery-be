package com.example.bakerybe.controller;

import com.example.bakerybe.dto.*;
import com.example.bakerybe.entity.Shift;
import com.example.bakerybe.entity.ShiftReport;
import com.example.bakerybe.entity.ShiftReportSummary;
import com.example.bakerybe.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shift-reports")
@RequiredArgsConstructor
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ResponseEntity<ShiftReport> startShift(@RequestParam Long bakeryId, @RequestParam Shift shift) {
        ShiftReport shiftReport = shiftReportService.startShift(bakeryId, shift);
        return ResponseEntity.ok(shiftReport);
    }

    @PutMapping("/{shiftReportId}/save")
    public ResponseEntity<ShiftReport> saveShift(@PathVariable Long shiftReportId,
                                                 @RequestBody List<ShiftReportProductUpdateRequest> updates) {
        ShiftReport shiftReport = shiftReportService.saveShift(shiftReportId, updates);
        return ResponseEntity.ok(shiftReport);
    }

    @PostMapping("/{id}/generate-report")
    public ShiftReportSummary generateShiftReport(@PathVariable Long id) {
        return shiftReportService.generateShiftReport(id);
    }

    @GetMapping("/reports")
    public List<ShiftReportSummary> getAllReports() {
        return shiftReportService.getAllReports();
    }
}

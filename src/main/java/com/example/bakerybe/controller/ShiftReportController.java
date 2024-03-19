package com.example.bakerybe.controller;

import com.example.bakerybe.dto.PageRequest;
import com.example.bakerybe.dto.ShiftEndInventoryReportRequest;
import com.example.bakerybe.dto.ShiftReportDto;
import com.example.bakerybe.dto.ShiftStartProductionReportRequest;
import com.example.bakerybe.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shift-reports")
@RequiredArgsConstructor
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ShiftReportDto recordShiftStartProduction(@RequestBody ShiftStartProductionReportRequest request){
        return shiftReportService.recordShiftStartProduction(request);
    }

    @PostMapping("/end")
    public ShiftReportDto recordShiftEndInventory(@RequestBody ShiftEndInventoryReportRequest request){
        return shiftReportService.recordShiftEndInventory(request);
    }

    @GetMapping("/{id}")
    public ShiftReportDto getById(@PathVariable Long id){
        return shiftReportService.getById(id);
    }

    @GetMapping("/filter")
    public Page<ShiftReportDto> getAllByBakeryIdAndProductId(@RequestParam Long bakeryId,
                                                             @RequestParam Long productId,
                                                             PageRequest pageRequest){
        return shiftReportService.getAllByBakeryIdAndProductId(bakeryId, productId, pageRequest);
    }

    @GetMapping("/{bakeryId}")
    public ResponseEntity<Page<ShiftReportDto>> getAllShiftsByBakeryId(@PathVariable Long bakeryId,
                                                                       PageRequest pageRequest
    ) {
        Page<ShiftReportDto> shiftReports = shiftReportService.getAllShiftsByBakeryId(bakeryId, pageRequest);
        return ResponseEntity.ok(shiftReports);
    }
    @PutMapping("/{id}/update")
    public ShiftReportDto updateShiftReportQuantities(@PathVariable Long id,
                                                      @RequestParam Integer producedQuantity,
                                                      @RequestParam Integer leftQuantity) {
        return shiftReportService.updateShiftReportQuantities(id, producedQuantity, leftQuantity);
    }
}

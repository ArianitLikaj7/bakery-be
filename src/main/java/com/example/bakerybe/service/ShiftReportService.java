package com.example.bakerybe.service;

import com.example.bakerybe.dao.ShiftReportRepository;
import com.example.bakerybe.dto.*;
import com.example.bakerybe.entity.ShiftReport;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.exception.ShiftReportAlreadyExistsException;
import com.example.bakerybe.mapper.ShiftReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final ProductService productService;
    private final ShiftReportMapper mapper;

    public ShiftReportDto recordShiftStartProduction(ShiftStartProductionReportRequest request){
        LocalDate todayDate = LocalDate.now();
        Boolean shiftReportExists = shiftReportRepository.existsByProductIdAndReportDateAndShift(request.productId(),
                todayDate,
                request.shift().name());
        if (shiftReportExists){
            throw new ShiftReportAlreadyExistsException(
                    String.format("Shift for product id %s and date %s already exists", request.productId(), todayDate));
        }
        ProductDto productById = productService.getById(request.productId());

        ShiftReport shiftReportInDb = shiftReportRepository.save(buildShiftReport(request, todayDate, productById));
        return mapper.toDto(shiftReportInDb);
    }


    public ShiftReportDto recordShiftEndInventory(ShiftEndInventoryReportRequest request){
        ShiftReport shiftReport = byId(request.shiftReportId());
        Integer quantityLeft = request.quantityLeft();
        BigDecimal earnings = calculateEarnings(shiftReport, quantityLeft);

        shiftReport.setLeftQuantity(quantityLeft);
        shiftReport.setDailyEarnings(earnings);

        return mapper.toDto(shiftReportRepository.save(shiftReport));
    }

    public ShiftReportDto getById(Long id){
        return mapper.toDto(byId(id));
    }

    public Page<ShiftReportDto> getAllByBakeryIdAndProductId(Long bakeryId, Long productId, PageRequest pageRequest){
        Page<ShiftReport> allByBakeryIdAndProductId = shiftReportRepository.findAllByBakeryIdAndProductId(bakeryId, productId, pageRequest.getPageable());

        return allByBakeryIdAndProductId.map(
                mapper::toDto
        );
    }

    private static ShiftReport buildShiftReport(ShiftStartProductionReportRequest request, LocalDate todayDate, ProductDto productById) {
        return ShiftReport.builder()
                .reportDate(todayDate)
                .shift(request.shift())
                .bakeryId(productById.getBakery().getId())
                .productId(productById.getId())
                .producedQuantity(request.quantityProduced())
                .build();
    }

    private ShiftReport byId(Long id) {
        return shiftReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shift Report with " + id + " not found"));
    }

    private BigDecimal calculateEarnings(ShiftReport shiftReport, Integer quantityLeft){
        BigDecimal productPrice = shiftReport.getProduct().getPrice();
        Integer producedQuantity = shiftReport.getProducedQuantity();

        int soldQuantity = producedQuantity - quantityLeft;

        return BigDecimal.valueOf(soldQuantity).multiply(productPrice);
    }
}

package com.example.bakerybe.service;

import com.example.bakerybe.dao.ProductRepository;
import com.example.bakerybe.dao.ShiftReportProductRepository;
import com.example.bakerybe.dao.ShiftReportRepository;
import com.example.bakerybe.dao.ShiftReportSummaryRepository;
import com.example.bakerybe.dto.ShiftReportProductSummaryDto;
import com.example.bakerybe.dto.ShiftReportProductUpdateRequest;
import com.example.bakerybe.dto.ShiftReportResponse;
import com.example.bakerybe.dto.ShiftReportSummaryDto;
import com.example.bakerybe.entity.*;
import com.example.bakerybe.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final ProductRepository productRepository;
    private final ShiftReportProductRepository shiftReportProductRepository;
    private final ShiftReportSummaryRepository shiftReportSummaryRepository;
    private final CustomUserService customUserService;

    @Transactional
    public ShiftReportResponse createShift(Long bakeryId, Shift shift) {
        ShiftReport shiftReport = ShiftReport.builder()
                .reportDate(LocalDate.now())
                .shift(shift)
                .bakeryId(bakeryId)
                .build();

        shiftReport = shiftReportRepository.save(shiftReport);
        log.info("Creating ShiftReport: bakeryId={}, shift={}, reportDate={}, shiftReportId={}",
                bakeryId, shift, LocalDate.now(), shiftReport.getId());

        List<Product> products = productRepository.findProductsByBakeryId(bakeryId);

        return new ShiftReportResponse(shiftReport.getId(), products);
    }


    @Transactional
    public Long saveShift(Long shiftReportId, List<ShiftReportProductUpdateRequest> updates) {
        ShiftReport shiftReport = shiftReportRepository.findById(shiftReportId)
                .orElseThrow(() -> new ResourceNotFoundException("ShiftReport not found"));

        for (ShiftReportProductUpdateRequest update : updates) {
            ShiftReportProduct product = shiftReportProductRepository
                    .findByShiftReportIdAndProductId(shiftReportId, update.getProductId())
                    .orElse(null);

            if (product == null) {
                product = ShiftReportProduct.builder()
                        .shiftReportId(shiftReportId)
                        .productId(update.getProductId())
                        .producedQuantity(update.getProducedQuantity())
                        .leftQuantity(update.getLeftQuantity())
                        .dailyEarnings(BigDecimal.ZERO)
                        .build();
            } else {
                product.setProducedQuantity(update.getProducedQuantity());
                product.setLeftQuantity(update.getLeftQuantity());
            }
            shiftReportProductRepository.save(product);
        }

        return shiftReport.getId();
    }

    @Transactional
    public ShiftReportSummaryDto generateShiftReport(Long shiftReportId) {
        ShiftReport shiftReport = shiftReportRepository.findById(shiftReportId)
                .orElseThrow(() -> new ResourceNotFoundException("ShiftReport not found"));

        List<ShiftReportProduct> reportProducts = shiftReportProductRepository
                .findShiftReportProductByShiftReportId(shiftReportId);

        AtomicReference<BigDecimal> totalDailyEarnings = new AtomicReference<>(BigDecimal.ZERO);

        List<ShiftReportProductSummaryDto> productSummaries = reportProducts.stream().map(product -> {
            BigDecimal productPrice = product.getProduct().getPrice();
            BigDecimal producedQuantity = BigDecimal.valueOf(product.getProducedQuantity());
            BigDecimal leftQuantity = BigDecimal.valueOf(product.getLeftQuantity());

            BigDecimal productProfit = producedQuantity.multiply(productPrice)
                    .subtract(leftQuantity.multiply(productPrice));

            totalDailyEarnings.updateAndGet(current -> current.add(productProfit));

            return new ShiftReportProductSummaryDto(
                    product.getProduct().getName(),
                    product.getProducedQuantity(),
                    product.getLeftQuantity(),
                    productPrice,
                    productProfit
            );
        }).collect(Collectors.toList());

        return new ShiftReportSummaryDto(
                shiftReport.getId(),
                shiftReport.getReportDate(),
                shiftReport.getShift(),
                productSummaries,
                totalDailyEarnings.get()
        );
    }

    public List<ShiftReportSummaryDto> getAllShiftReports() {
        List<ShiftReport> reports = shiftReportRepository.findAllByOrderByReportDateDesc();

        return reports.stream().map(shiftReport -> {
            List<ShiftReportProduct> reportProducts =
                    shiftReportProductRepository.findShiftReportProductByShiftReportId(shiftReport.getId());

            AtomicReference<BigDecimal> totalDailyEarnings = new AtomicReference<>(BigDecimal.ZERO);

            List<ShiftReportProductSummaryDto> productSummaries = reportProducts.stream().map(product -> {
                BigDecimal productPrice = product.getProduct().getPrice();
                BigDecimal producedQuantity = BigDecimal.valueOf(product.getProducedQuantity());
                BigDecimal leftQuantity = BigDecimal.valueOf(product.getLeftQuantity());

                BigDecimal productProfit = producedQuantity.multiply(productPrice)
                        .subtract(leftQuantity.multiply(productPrice));

                totalDailyEarnings.updateAndGet(current -> current.add(productProfit));

                return new ShiftReportProductSummaryDto(
                        product.getProduct().getName(),
                        product.getProducedQuantity(),
                        product.getLeftQuantity(),
                        productPrice,
                        productProfit
                );
            }).collect(Collectors.toList());

            return new ShiftReportSummaryDto(
                    shiftReport.getId(),
                    shiftReport.getReportDate(),
                    shiftReport.getShift(),
                    productSummaries,
                    totalDailyEarnings.get()
            );
        }).toList();
    }

}

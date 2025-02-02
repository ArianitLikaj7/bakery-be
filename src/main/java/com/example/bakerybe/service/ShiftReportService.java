package com.example.bakerybe.service;

import com.example.bakerybe.dao.ProductRepository;
import com.example.bakerybe.dao.ShiftReportProductRepository;
import com.example.bakerybe.dao.ShiftReportRepository;
import com.example.bakerybe.dao.ShiftReportSummaryRepository;
import com.example.bakerybe.dto.*;
import com.example.bakerybe.entity.*;
import com.example.bakerybe.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final ProductRepository productRepository;
    private final ShiftReportProductRepository shiftReportProductRepository;
    private final ShiftReportSummaryRepository shiftReportSummaryRepository;
    private final CustomUserService customUserService;

    @Transactional
    public ShiftReport startShift(Long bakeryId, Shift shift) {
        ShiftReport shiftReport = ShiftReport.builder()
                .reportDate(LocalDate.now())
                .shift(shift)
                .bakeryId(bakeryId)
                .build();

        shiftReport = shiftReportRepository.save(shiftReport);

        List<Product> products = productRepository.findProductsByBakeryId(bakeryId);

        ShiftReport finalShiftReport = shiftReport;
        List<ShiftReportProduct> reportProducts = products.stream()
                .map(product -> ShiftReportProduct.builder()
                        .shiftReportId(finalShiftReport.getId())
                        .productId(product.getId())
                        .producedQuantity(0)
                        .leftQuantity(0)
                        .dailyEarnings(BigDecimal.ZERO)
                        .build())
                .toList();

        shiftReportProductRepository.saveAll(reportProducts);

        List<ShiftReportProduct> shiftProducts = shiftReportProductRepository.findByShiftReportId(shiftReport.getId());
        shiftReport.setShiftReportProducts(shiftProducts);

        return shiftReport;
    }


    @Transactional
    public ShiftReport saveShift(Long shiftReportId, List<ShiftReportProductUpdateRequest> updates) {
        ShiftReport shiftReport = shiftReportRepository.findById(shiftReportId)
                .orElseThrow(() -> new ResourceNotFoundException("ShiftReport not found"));

        // Sigurohemi që lista nuk është null
        if (shiftReport.getShiftReportProducts() == null) {
            shiftReport.setShiftReportProducts(new ArrayList<>());
        }

        for (ShiftReportProductUpdateRequest update : updates) {
            ShiftReportProduct product = shiftReport.getShiftReportProducts().stream()
                    .filter(p -> p.getProduct().getId().equals(update.getProductId()))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                // Krijo një entitet të ri nëse nuk ekziston
                product = new ShiftReportProduct();
                product.setShiftReport(shiftReport);
                product.setProduct(productRepository.findById(update.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                shiftReport.getShiftReportProducts().add(product);
            }

            // Përditëso vlerat
            product.setProducedQuantity(product.getProducedQuantity() + update.getProducedQuantity());
            product.setLeftQuantity(product.getLeftQuantity() + update.getLeftQuantity());
            product.setDailyEarnings(product.getDailyEarnings().add(update.getDailyEarnings()));
        }

        return shiftReport;
    }






    @Transactional
    public ShiftReportSummary generateShiftReport(Long shiftReportId) {
        ShiftReport shiftReport = shiftReportRepository.findById(shiftReportId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("ShiftReport with id %s not found", shiftReportId)));

        List<ShiftReportProduct> reportProducts = shiftReportProductRepository.findShiftReportProductByShiftReportId(shiftReportId);

        BigDecimal totalDailyEarnings = BigDecimal.ZERO;
        List<ShiftReportProductSummary> productSummaries = new ArrayList<>();

        for (ShiftReportProduct product : reportProducts) {
            BigDecimal productPrice = product.getProduct().getPrice();
            BigDecimal producedQuantity = BigDecimal.valueOf(product.getProducedQuantity());
            BigDecimal leftQuantity = BigDecimal.valueOf(product.getLeftQuantity());

            BigDecimal productProfit = producedQuantity.multiply(productPrice)
                    .subtract(leftQuantity.multiply(productPrice));

            totalDailyEarnings = totalDailyEarnings.add(productProfit);

            ShiftReportProductSummary productSummary = ShiftReportProductSummary.builder()
                    .productName(product.getProduct().getName())
                    .producedQuantity(product.getProducedQuantity())
                    .leftQuantity(product.getLeftQuantity())
                    .productPrice(productPrice)
                    .productProfit(productProfit)
                    .build();

            productSummaries.add(productSummary);
        }

        ShiftReportSummary shiftReportSummary = ShiftReportSummary.builder()
                .reportDate(shiftReport.getReportDate())
                .shift(shiftReport.getShift())
                .totalDailyEarnings(totalDailyEarnings)
                .productSummaries(productSummaries)
                .createdBy(customUserService.getCurrentUser().getId())
                .build();

        ShiftReportSummary savedReport = shiftReportSummaryRepository.save(shiftReportSummary);
        productSummaries.forEach(p -> p.setShiftReportSummary(savedReport));
        return savedReport;
    }

    public List<ShiftReportSummary> getAllReports() {
        return shiftReportSummaryRepository.findAll();
    }

}

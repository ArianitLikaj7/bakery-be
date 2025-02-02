package com.example.bakerybe.dao;

import com.example.bakerybe.entity.ShiftReportProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftReportProductRepository extends JpaRepository<ShiftReportProduct, Long> {
    List<ShiftReportProduct> findShiftReportProductByShiftReportId(Long shiftReportId);
    List<ShiftReportProduct> findByShiftReportId(Long shiftReportId);
    Optional<ShiftReportProduct> findByShiftReportIdAndProductId(Long shiftReportId, Long productId);


}

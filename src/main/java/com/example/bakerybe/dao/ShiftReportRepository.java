package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Shift;
import com.example.bakerybe.entity.ShiftReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {

    Boolean existsByProductIdAndReportDateAndShift(Long productId,
                                                   LocalDate reportDate,
                                                   Shift shift);

    @Query(value = "select * from shift_report" +
            " where bakery_id = :bakeryId" +
            " and product_id = :productId",
            countQuery = "select * from shift_report" +
                    " where bakery_id = :bakeryId" +
                    " and product_id = :productId",
            nativeQuery = true)
    Page<ShiftReport> findAllByBakeryIdAndProductId(Long bakeryId,
                                                    Long productId,
                                                    Pageable pageable);

    @Query(value = "SELECT * FROM shift_report s WHERE s.bakery_id = :bakeryId", nativeQuery = true)
    Page<ShiftReport> findAllByBakeryId(Long bakeryId, Pageable pageable);
}

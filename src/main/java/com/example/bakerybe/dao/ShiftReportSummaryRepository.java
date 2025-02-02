package com.example.bakerybe.dao;

import com.example.bakerybe.entity.ShiftReportSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftReportSummaryRepository extends JpaRepository<ShiftReportSummary, Long> {
    List<ShiftReportSummary> findByReportDate(LocalDate reportDate);
}
package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Shift;
import com.example.bakerybe.entity.ShiftReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {
    List<ShiftReport> findAllByOrderByReportDateDesc();
}

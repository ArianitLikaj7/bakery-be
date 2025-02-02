package com.example.bakerybe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shift_report_summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftReportSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    private BigDecimal totalDailyEarnings;

    @Column(name = "created_by")
    private UUID createdBy;

    @OneToMany(mappedBy = "shiftReportSummary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShiftReportProductSummary> productSummaries;
}

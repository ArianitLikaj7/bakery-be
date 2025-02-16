package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shift_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftReport extends BaseEntity {

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift", nullable = false)
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "bakery_id", insertable = false, updatable = false)
    private Bakery bakery;

    @Column(name = "bakery_id")
    private Long bakeryId;

    @OneToMany(mappedBy = "shiftReport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ShiftReportProduct> shiftReportProducts = new ArrayList<>();


    public ShiftReport(Long id, LocalDate reportDate, Shift shift, Bakery bakery, Long bakeryId, Object o) {
        super();
    }
}

package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.entity.Product;
import com.example.bakerybe.entity.Shift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftReportDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate reportDate;
    private Shift shift;
    private ProductDto product;
    private BakeryDto bakery;
    private Integer producedQuantity;
    private Integer leftQuantity;
    private BigDecimal dailyEarnings;
}

package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShiftReportResponse {
    private Long shiftReportId;
    private List<Product> products;
}

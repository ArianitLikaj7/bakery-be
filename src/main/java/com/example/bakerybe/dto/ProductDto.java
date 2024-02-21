package com.example.bakerybe.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends BaseDto {
    private String name;
    private String description;
    private BigDecimal price;
    private BakeryDto bakery;
}

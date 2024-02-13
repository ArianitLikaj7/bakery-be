package com.example.bakerybe.dto;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        Long bakeryId) {
}

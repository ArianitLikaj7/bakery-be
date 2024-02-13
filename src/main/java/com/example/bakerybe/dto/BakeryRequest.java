package com.example.bakerybe.dto;

public record BakeryRequest(
        String name,
        String address,
        String city,
        String country,
        Long tenantId) {
}

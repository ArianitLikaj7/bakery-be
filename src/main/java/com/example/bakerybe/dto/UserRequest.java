package com.example.bakerybe.dto;

public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Long tenantId
) {
}

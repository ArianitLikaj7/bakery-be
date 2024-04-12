package com.example.bakerybe.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CurrentLoggedInUserDto(
        UUID userId,
        String firstName,
        String lastName,
        String email,
        String role,
        Boolean hasBranches ) {
}

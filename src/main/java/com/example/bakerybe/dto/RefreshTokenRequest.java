package com.example.bakerybe.dto;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(@NotNull String token) {
}

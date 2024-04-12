package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Role;

public record AuthenticationResponse(String token, String refreshToken, Role role, Boolean hasBranches) {
}

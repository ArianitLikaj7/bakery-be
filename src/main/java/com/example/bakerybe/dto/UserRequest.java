package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Role;

public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Role role,
        Long tenantId
) {
}

package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Role;

public record CustomUserRequest(
            String firstName,
            String lastName,
            String username,
            String password,
            Role role,
            Long bakeryId
) {

}



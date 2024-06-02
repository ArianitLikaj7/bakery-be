package com.example.bakerybe.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record CurrentLoggedInCustomUserDto (UUID userId,
                                           String firstName,
                                           String lastName,
                                           String email,
                                           String role,
                                           Boolean hasBranches,
                                           Long bakeryId) implements CurrentLoggedIn{
}

package com.example.bakerybe.dto;

import java.util.UUID;

public record TenantRequest(
        String businessName,
        String identificationNumber,
        UUID tenantOwnerId
) {
}

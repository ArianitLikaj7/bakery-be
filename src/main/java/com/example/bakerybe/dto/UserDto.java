package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
    private TenantDto tenant;
    private Boolean hasBranches;
    private List<TenantDto> ownerOfTenants;
}

package com.example.bakerybe.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantDto extends BaseDto{

    private String businessName;
    private String identificationNumber;
    private UserDto tenantOwner;
}

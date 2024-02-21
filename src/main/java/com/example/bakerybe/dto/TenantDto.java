package com.example.bakerybe.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantDto extends BaseDto{

    private String businessName;
    private String identificationNumber;
    @JsonBackReference
    private UserDto tenantOwner;
}

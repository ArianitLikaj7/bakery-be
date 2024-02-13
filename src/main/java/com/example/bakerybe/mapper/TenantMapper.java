package com.example.bakerybe.mapper;

import com.example.bakerybe.dto.TenantDto;
import com.example.bakerybe.dto.TenantRequest;
import com.example.bakerybe.entity.Tenant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantMapper implements GenericMapper<Tenant, TenantDto, TenantRequest>{

    private final ModelMapper modelMapper;

    @Override
    public TenantDto toDto(Tenant entity) {
        return modelMapper.map(entity, TenantDto.class);
    }

    @Override
    public Tenant toEntity(TenantRequest request) {
        return modelMapper.map(request, Tenant.class);
    }
}

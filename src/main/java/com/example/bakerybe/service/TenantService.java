package com.example.bakerybe.service;

import com.example.bakerybe.dao.TenantRepository;
import com.example.bakerybe.dto.TenantDto;
import com.example.bakerybe.dto.TenantRequest;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.entity.EntityStatus;
import com.example.bakerybe.entity.Role;
import com.example.bakerybe.entity.Tenant;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.TenantMapper;
import com.example.bakerybe.util.ReflectionUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper mapper;
    private final UserService userService;

    public TenantDto create(TenantRequest request){
        Tenant tenant = mapper.toEntity(request);
        mapTenantOwner(tenant, request.tenantOwnerId());
        tenant.setStatus(EntityStatus.ACTIVE);
        Tenant tenantInDb = tenantRepository.save(tenant);
        return mapper.toDto(tenantInDb);
    }

    public TenantDto getById(Long id){
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tenant with id %s not found", id)));
        return mapper.toDto(tenant);
    }

    public List<TenantDto> getAll(){
        return tenantRepository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public TenantDto update(Long id, Map<String, Object> fields){
        Tenant tenantInDb = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tenant with id %s not found", id)));

        fields.forEach((key, value) -> {
            ReflectionUtil.setFieldValue(tenantInDb, key, value);
        });

        return mapper.toDto(tenantRepository.save(tenantInDb));
    }

    private void mapTenantOwner(Tenant tenant, UUID id){
        UserDto user = userService.getById(id);

        if (!user.getRole().equals(Role.ADMIN)){
            throw new ValidationException("Tenant Owner must be Admin Role");
        }

        tenant.setTenantOwnerId(id);
    }
}

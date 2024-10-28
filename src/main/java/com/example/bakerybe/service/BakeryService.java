package com.example.bakerybe.service;

import com.example.bakerybe.dao.BakeryRepository;
import com.example.bakerybe.dto.BakeryDto;
import com.example.bakerybe.dto.BakeryRequest;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.BakeryMapper;
import com.example.bakerybe.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BakeryService {
    private final BakeryRepository bakeryRepository;
    private final BakeryMapper mapper;
    private final UserService userService;

    public BakeryDto create(BakeryRequest request){
        UserDto currentUser = userService.getCurrentUser();
        Bakery bakery = mapper.toEntity(request);

        if (currentUser.getOwnerOfTenants() != null && !currentUser.getOwnerOfTenants().isEmpty()) {
            bakery.setTenantId(currentUser.getOwnerOfTenants().get(0).getId());
        } else {
            // Handle the case where there are no tenants owned by the user
            throw new IllegalStateException("Current user does not own any tenants.");
        }

        // Update user to have branches, make sure to handle exceptions properly
        try {
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("hasBranches", true);
            userService.update(currentUser.getId(), updateFields);
        } catch (Exception e) {
            log.error("Failed to update user with ID: {}", currentUser.getId(), e);
            throw e; // rethrow or handle as needed
        }

        Bakery bakeryInDb = bakeryRepository.save(bakery);
        return mapper.toDto(bakeryInDb);
    }


    public BakeryDto getById(Long id) {
        UserDto currentUser = userService.getCurrentUser();
        Long tenantId = Long.valueOf(currentUser.getOwnerOfTenants().get(0).getId());

        Bakery bakery = bakeryRepository.findById(id)
                .filter(b -> b.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bakery with id %s not found for the current user", id)));

        return mapper.toDto(bakery);
    }


    public List<BakeryDto> getAll(){
        List<Bakery> bakeries = bakeryRepository.findAll();
       return bakeries.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BakeryDto> getAllByTenant() {
        UserDto currentUser = userService.getCurrentUser();
        Long tenantId = Long.valueOf(currentUser.getOwnerOfTenants().get(0).getId());
        List<Bakery> bakeriesByTenantId = bakeryRepository.findByTenantId(tenantId);
        return bakeriesByTenantId.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public BakeryDto update(Long id, Map<String, Object> fields) {
        Bakery bakeryInDb = bakeryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bakery with id %s not found",id)));
      fields.forEach((key, value) -> {
           ReflectionUtil.setFieldValue(bakeryInDb,key, value);
        });
      return mapper.toDto(bakeryRepository.save(bakeryInDb));
    }

    public void deleteById(Long id){
        bakeryRepository.deleteById(id);
    }
}

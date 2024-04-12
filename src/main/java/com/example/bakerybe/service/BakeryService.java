package com.example.bakerybe.service;

import com.example.bakerybe.dao.BakeryRepository;
import com.example.bakerybe.dto.BakeryDto;
import com.example.bakerybe.dto.BakeryRequest;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.entity.User;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.BakeryMapper;
import com.example.bakerybe.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BakeryService {
    private final BakeryRepository bakeryRepository;
    private final BakeryMapper mapper;
    private final UserService userService;

    public BakeryDto create(BakeryRequest request){
        UserDto currentUser = userService.getCurrentUser();
        Bakery bakery = mapper.toEntity(request);
        bakery.setTenantId(currentUser.getOwnerOfTenants().get(0).getId());
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("hasBranches", true);
        userService.update(currentUser.getId(), updateFields);
        Bakery bakeryInDb = bakeryRepository.save(bakery);
        return mapper.toDto(bakeryInDb);
    }

    public BakeryDto getById(Long id) {
        Bakery bakery = bakeryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Bakery with id %s not found",id)));
        return mapper.toDto(bakery);
    }

    public List<BakeryDto> getAll(){
        List<Bakery> bakeries = bakeryRepository.findAll();
       return bakeries.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BakeryDto> getAllByTenant(Long tenantId) {
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
}

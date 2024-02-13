package com.example.bakerybe.service;

import com.example.bakerybe.dao.BakeryRepository;
import com.example.bakerybe.dao.TenantRepository;
import com.example.bakerybe.dto.BakeryDto;
import com.example.bakerybe.dto.BakeryRequest;
import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.entity.Tenant;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.BakeryMapper;
import com.example.bakerybe.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BakeryService {
    private final BakeryRepository bakeryRepository;
    private final BakeryMapper mapper;
    private final TenantRepository tenantRepository;

    @Autowired
    public BakeryService(BakeryRepository bakeryRepository, BakeryMapper bakeryMapper,
                         TenantRepository tenantRepository) {
        this.bakeryRepository = bakeryRepository;
        this.mapper = bakeryMapper;
        this.tenantRepository = tenantRepository;
    }

    public BakeryDto create(BakeryRequest request){
        Tenant tenantInDb = tenantRepository.findById(request.tenantId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tenant with id %s not found",request.tenantId())));
        Bakery bakery = mapper.toEntity(request);
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

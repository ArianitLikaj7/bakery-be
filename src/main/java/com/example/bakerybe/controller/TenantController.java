package com.example.bakerybe.controller;

import com.example.bakerybe.dto.TenantDto;
import com.example.bakerybe.dto.TenantRequest;
import com.example.bakerybe.service.TenantService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantDto> create(@RequestBody @NotNull TenantRequest request){
        return new ResponseEntity<>(tenantService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(tenantService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getAll(){
        return ResponseEntity.ok(tenantService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> update(@PathVariable Long id,
                                            @RequestBody TenantRequest tenantRequest){
        return ResponseEntity.ok(tenantService.update(id, tenantRequest));
    }
}

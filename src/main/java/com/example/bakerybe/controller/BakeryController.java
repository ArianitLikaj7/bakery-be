package com.example.bakerybe.controller;

import com.example.bakerybe.dto.BakeryDto;
import com.example.bakerybe.dto.BakeryRequest;
import com.example.bakerybe.service.BakeryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/bakeries")
@RequiredArgsConstructor
public class BakeryController {
    private final BakeryService bakeryService;

    @PostMapping
    public ResponseEntity<BakeryDto> create(@RequestBody @NotNull BakeryRequest request) {
        return new ResponseEntity<>(bakeryService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BakeryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bakeryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BakeryDto>> getAll() {
        return ResponseEntity.ok(bakeryService.getAll());
    }

    @GetMapping("/by-tenant")
    public ResponseEntity<List<BakeryDto>> getByTenant() {
        return ResponseEntity.ok(bakeryService.getAllByTenant());
    }

    @PutMapping ("/{id}")
    public ResponseEntity<BakeryDto> update(@PathVariable Long id,
                                            @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(bakeryService.update(id, fields));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        bakeryService.deleteById(id);
    }
}

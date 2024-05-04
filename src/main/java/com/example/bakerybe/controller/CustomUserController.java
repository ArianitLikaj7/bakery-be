package com.example.bakerybe.controller;

import com.example.bakerybe.dao.CustomUserRepository;
import com.example.bakerybe.dto.CustomUserDto;
import com.example.bakerybe.dto.CustomUserRequest;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customusers")
@RequiredArgsConstructor
public class CustomUserController {

    private final CustomUserService customUserService;

    @PostMapping
    public ResponseEntity<CustomUserDto> create(@RequestBody CustomUserRequest request){
        return ResponseEntity.ok(customUserService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomUserDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(customUserService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomUserDto>> getAll(){
        return ResponseEntity.ok(customUserService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomUserDto> update(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(customUserService.update(id,fields));
    }

}

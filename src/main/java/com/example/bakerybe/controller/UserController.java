package com.example.bakerybe.controller;

import com.example.bakerybe.dao.CustomUserRepository;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.dto.UserRequest;
import com.example.bakerybe.entity.CustomUser;
import com.example.bakerybe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CustomUserRepository customUserRepository;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserRequest request){
        return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>>getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(userService.update(id,fields));
    }

    @GetMapping("/custom-users/{bakeryId}")
    public List<CustomUser> getCustomUsersByBakeryId(@PathVariable Long bakeryId) {
        return customUserRepository.findByBakeryId(bakeryId);
    }
}

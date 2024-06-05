package com.example.bakerybe.service;

import com.example.bakerybe.dao.CustomUserRepository;
import com.example.bakerybe.dto.*;
import com.example.bakerybe.entity.CustomUser;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.CustomUserMapper;
import com.example.bakerybe.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserService {

    private final CustomUserRepository repository;
    private final CustomUserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ProductService productService;

    public CustomUserDto create(CustomUserRequest request){
        CustomUser customUser = mapper.toEntity(request);
        setUserPasswordAndRole(request, customUser);
        UserDto user = userService.getCurrentUser();
        customUser.setCreatedBy(user.getUsername());
        CustomUser userInDb = repository.save(customUser);
        return mapper.toDto(userInDb);
    }

    public List<ProductDto> getProducts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return productService.getProductsByBakeryId(customUser.getBakeryId());
    }
    public CustomUserDto getById(Long id){
        CustomUser user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("CustomUser with id %s not found", id)));
        return mapper.toDto(user);
    }

    public CustomUserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser loggedUser = (CustomUser) authentication.getPrincipal();
        return mapper.toDto(loggedUser);
    }

    public List<CustomUserDto> getAll(){
        List<CustomUser> users = repository.findAll();
        return users.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    public CustomUserDto update(Long id, Map<String, Object> fields){
        CustomUser userInDb = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("CustomUser with id %s not found", id)));
        fields.forEach((key, value) ->{
            ReflectionUtil.setFieldValue(userInDb, key, value);
        });
        return mapper.toDto(repository.save(userInDb));
    }

    private void setUserPasswordAndRole(CustomUserRequest request, CustomUser user) {
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
    }
}

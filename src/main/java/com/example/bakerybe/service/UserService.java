package com.example.bakerybe.service;

import com.example.bakerybe.dao.CustomUserRepository;
import com.example.bakerybe.dao.UserRepository;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.dto.UserRequest;
import com.example.bakerybe.entity.CustomUser;
import com.example.bakerybe.entity.User;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.UserMapper;
import com.example.bakerybe.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto create(UserRequest request){
        User user = mapper.toEntity(request);
        setUserPasswordAndRole(request, user);
        User userInDb = userRepository.save(user);
        return mapper.toDto(userInDb);
    }

    public UserDto getById(UUID id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s not found", id)));
        return mapper.toDto(user);
    }

    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) authentication.getPrincipal();

        return mapper.toDto(loggedUser);
    }

    public List<UserDto> getAll(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto update(UUID id, UserRequest updatedUser) {
        User userInDb = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %s not found", id)));

        userInDb.setFirstName(updatedUser.firstName());
        userInDb.setLastName(updatedUser.lastName());
        userInDb.setUsername(updatedUser.username());
        userInDb.setPassword(updatedUser.password());
        userInDb.setRole(updatedUser.role());
        userInDb.setTenantId(updatedUser.tenantId());

        userRepository.save(userInDb);

        return mapper.toDto(userInDb);
    }

    public UserDto updateUserOnlyForBakery(UUID id, Map<String, Object> fields){
        User userInDb = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("User with id %s not found", id)));
        fields.forEach((key, value) ->{
            ReflectionUtil.setFieldValue(userInDb, key, value);
        });
        return mapper.toDto(userRepository.save(userInDb));
    }
    public List<CustomUser> getCustomUsersByBakeryId(Long bakeryId) {
        return customUserRepository.findByBakeryId(bakeryId);
    }
    private void setUserPasswordAndRole(UserRequest request, User user) {
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
    }
}

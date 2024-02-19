package com.example.bakerybe.service;

import com.example.bakerybe.dao.TenantRepository;
import com.example.bakerybe.dao.UserRepository;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.dto.UserRequest;
import com.example.bakerybe.entity.Role;
import com.example.bakerybe.entity.Tenant;
import com.example.bakerybe.entity.User;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.UserMapper;
import com.example.bakerybe.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

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

    public List<UserDto> getAll(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto update(UUID id, Map<String, Object> fields){
        User userInDb = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("User with id %s not found", id)));
        fields.forEach((key, value) ->{
            ReflectionUtil.setFieldValue(userInDb, key, value);
        });
        return mapper.toDto(userRepository.save(userInDb));
    }

    private void setUserPasswordAndRole(UserRequest request, User user) {
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
    }
}

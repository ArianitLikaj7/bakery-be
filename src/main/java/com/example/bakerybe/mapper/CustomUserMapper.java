package com.example.bakerybe.mapper;

import com.example.bakerybe.dto.CustomUserDto;
import com.example.bakerybe.dto.CustomUserRequest;
import com.example.bakerybe.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserMapper implements GenericMapper<CustomUser,CustomUserDto, CustomUserRequest>{

    private final ModelMapper mapper;
    @Override
    public CustomUserDto toDto(CustomUser entity) {
        return mapper.map(entity, CustomUserDto.class);
    }

    @Override
    public CustomUser toEntity(CustomUserRequest request) {
        return mapper.map(request, CustomUser.class);
    }
}

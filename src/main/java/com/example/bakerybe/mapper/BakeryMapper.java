package com.example.bakerybe.mapper;

import com.example.bakerybe.dto.BakeryDto;
import com.example.bakerybe.dto.BakeryRequest;
import com.example.bakerybe.entity.Bakery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BakeryMapper implements GenericMapper<Bakery, BakeryDto, BakeryRequest>{
    private final ModelMapper modelMapper;


    @Override
    public BakeryDto toDto(Bakery entity) {
        return modelMapper.map(entity, BakeryDto.class);
    }

    @Override
    public Bakery toEntity(BakeryRequest request) {
        return modelMapper.map(request, Bakery.class);
    }
}

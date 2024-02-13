package com.example.bakerybe.mapper;

import com.example.bakerybe.dto.ProductDto;
import com.example.bakerybe.dto.ProductRequest;
import com.example.bakerybe.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements GenericMapper<Product, ProductDto, ProductRequest>{
    private final ModelMapper modelMapper;

    @Override
    public ProductDto toDto(Product entity) {
        return modelMapper.map(entity, ProductDto.class);
    }

    @Override
    public Product toEntity(ProductRequest request) {
        return modelMapper.map(request, Product.class);
    }
}

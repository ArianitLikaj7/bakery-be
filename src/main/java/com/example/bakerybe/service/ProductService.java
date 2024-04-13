package com.example.bakerybe.service;

import com.example.bakerybe.dao.BakeryRepository;
import com.example.bakerybe.dao.ProductRepository;
import com.example.bakerybe.dto.ProductDto;
import com.example.bakerybe.dto.ProductRequest;
import com.example.bakerybe.dto.UserDto;
import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.entity.Product;
import com.example.bakerybe.exception.ResourceNotFoundException;
import com.example.bakerybe.mapper.ProductMapper;
import com.example.bakerybe.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final BakeryRepository bakeryRepository;
    private final UserService userService;



    public ProductDto create(ProductRequest request) {
        Bakery bakeryInDb = bakeryRepository.findById(request.bakeryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bakery with id %s not found", request.bakeryId())));
        Product product = mapper.toEntity(request);
        Product productInDb = productRepository.save(product);
        return mapper.toDto(productInDb);
    }

    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Product with id %s not found", id)));
        return mapper.toDto(product);
    }

    public List<ProductDto> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteByBakeryId(Long productId, String username) {

        Bakery bakery = bakeryRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Bakery not found for user %s", username)));

        Product product = productRepository.findByIdAndBakery(productId, bakery)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with id %s not found in bakery of user %s", productId, username)));

        productRepository.delete(product);
    }

    public ProductDto update(Long id, Map<String, Object> fields) {
        Product productInDb = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with id %s not found", id)));
        fields.forEach((key, value)-> {
            ReflectionUtil.setFieldValue(productInDb,key,value);
        });
        return mapper.toDto(productInDb);
    }
}

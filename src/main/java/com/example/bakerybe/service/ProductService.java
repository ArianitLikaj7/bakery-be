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

import java.math.BigDecimal;
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

    public List<ProductDto> getProductsByBakeryId(Long id){
        List<Product> products = productRepository.findProductsByBakeryId(id);
        return products.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto update(Long id, Map<String, Object> fields) {
        Product productInDb = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with id %s not found", id)));

        if (fields.containsKey("name")) {
            productInDb.setName((String) fields.get("name"));
        }
        if (fields.containsKey("description")) {
            productInDb.setDescription((String) fields.get("description"));
        }
        if (fields.containsKey("price")) {
            String priceStr = (String) fields.get("price");
            BigDecimal price = new BigDecimal(priceStr);
            productInDb.setPrice(price);
        }
        if (fields.containsKey("bakeryId")) {
            productInDb.setBakeryId((Long) fields.get("bakeryId"));
        }

        productRepository.save(productInDb);

        return mapper.toDto(productInDb);
    }


    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}

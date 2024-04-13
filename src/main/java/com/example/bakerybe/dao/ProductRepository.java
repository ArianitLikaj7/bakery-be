package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndBakery(Long productId, Bakery bakery);
}

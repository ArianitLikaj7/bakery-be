package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

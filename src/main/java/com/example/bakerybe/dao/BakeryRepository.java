package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Bakery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {
    List<Bakery> findByTenantId(Long tenantId);
}

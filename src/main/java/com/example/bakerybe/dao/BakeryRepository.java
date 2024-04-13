package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Bakery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {
    List<Bakery> findByTenantId(Long tenantId);

}

package com.example.bakerybe.dao;

import com.example.bakerybe.entity.Bakery;
import com.example.bakerybe.entity.CustomUser;
import com.example.bakerybe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    @Query("SELECT u FROM CustomUser u WHERE u.bakeryId = :bakeryId")
    List<CustomUser> findByBakeryId(@Param("bakeryId") Long bakeryId);





}

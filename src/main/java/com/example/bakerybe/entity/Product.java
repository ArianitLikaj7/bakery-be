package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@Entity
public class Product extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;

    @Column(name = "bakery_id")
    private Long bakeryId;

    @ManyToOne
    @JoinColumn(name = "bakery_id", insertable = false, updatable = false)
    @JsonManagedReference
    private Bakery bakery;
}


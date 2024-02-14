package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Bakery bakery; // No need for JsonIgnore, as we want bakery ID when serializing Product
}

package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bakeries")
@Entity
public class Bakery extends BaseEntity {

    private String name;
    private String address;
    private String city;
    private String country;

    @Column(name = "tenant_id")
    private Long tenantId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    @JsonManagedReference
    private Tenant tenant;

    @OneToMany(mappedBy = "bakery", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Product> products;
}

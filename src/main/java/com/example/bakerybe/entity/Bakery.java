package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Tenant tenant;

    @OneToMany(mappedBy = "bakery", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("bakery")
    private Set<Product> products;

}

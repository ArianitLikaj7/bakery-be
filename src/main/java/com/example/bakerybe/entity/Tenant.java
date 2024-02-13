package com.example.bakerybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenants")
@Entity
public class Tenant extends BaseEntity{

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "identification_number")
    private String identificationNumber;

    @OneToMany(mappedBy = "tenant")
    @JsonBackReference
    private Set<Bakery> bakeries;

    @Column(name = "tenant_owner_user_id")
    private UUID tenantOwnerId;

    @ManyToOne
    @JoinColumn(name = "tenant_owner_user_id", insertable = false, updatable = false)
    private User tenantOwner;

    @OneToMany(mappedBy = "tenant")
    @JsonBackReference
    private Set<User> users;

    @Enumerated(value = EnumType.STRING)
    private EntityStatus status;
}

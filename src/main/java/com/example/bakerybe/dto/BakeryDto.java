package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BakeryDto extends BaseDto{
    private String name;
    private String address;
    private String city;
    private String country;
    @JsonBackReference
    private Set<ProductDto> products;
}

package com.example.bakerybe.dto;

import com.example.bakerybe.entity.Product;
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
    private Set<Product> products;
}

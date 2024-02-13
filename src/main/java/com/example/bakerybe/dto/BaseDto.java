package com.example.bakerybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.example.basket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CargoOptionDTO{

    private String companyName;
    private Double servicePrice;
}

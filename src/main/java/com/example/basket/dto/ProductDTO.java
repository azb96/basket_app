package com.example.basket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String image;
    private String title;
    private Integer quantity;
    private Double price;

    private Long customerId;
    private String customerEmail;
}

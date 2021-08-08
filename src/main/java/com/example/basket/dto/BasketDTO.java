package com.example.basket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketDTO {
    private Long id;
    private String ownerEmail;
}

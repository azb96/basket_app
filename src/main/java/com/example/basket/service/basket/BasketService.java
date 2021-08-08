package com.example.basket.service.basket;

import com.example.basket.dto.CargoOptionDTO;
import com.example.basket.dto.ProductDTO;
import com.example.basket.resource.BasketResource;
import com.example.basket.resource.CargoOptionResource;
import com.example.basket.resource.ProductResource;

public interface BasketService {

    ProductResource addProduct(Long basketId, ProductDTO productDTO);
    boolean removeProduct(Long basketId, ProductDTO productDTO);
    BasketResource getBasket(Long basketId, Long customerId);
    boolean addCargoOption(Long basketId, CargoOptionDTO cargoOptionDTO);
}

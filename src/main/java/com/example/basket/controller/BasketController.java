package com.example.basket.controller;

import com.example.basket.constant.BasketServiceConstants;
import com.example.basket.dto.CargoOptionDTO;
import com.example.basket.dto.ProductDTO;
import com.example.basket.resource.BasketResource;
import com.example.basket.resource.CargoOptionResource;
import com.example.basket.resource.ProductResource;
import com.example.basket.service.basket.BasketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BasketServiceConstants.REQUEST_ACCOUNT_BASE_URL)
public class BasketController {
    Logger logger = LoggerFactory.getLogger(BasketController.class);
    private final BasketService basketService;

    @Autowired
    public BasketController(final BasketService basketService){
        this.basketService = basketService;
    }

    //add product to basket
    @PostMapping(path = "{basketId}/add")
    @ApiOperation(value = "Add product to the basket")
    public ResponseEntity<ProductResource> addProduct(@ApiParam(value = "Basket Identifier", required = true) @PathVariable("basketId") Long basketId,
                                                      @ApiParam(value = "Product resource", required = true) @RequestBody ProductDTO productDTO){
        logger.trace("Received add product request for {}", productDTO.getTitle());

        boolean result = basketService.addProduct(basketId, productDTO);

        if(result){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    //remove product from basket
    @DeleteMapping(path = "{basketId}/remove")
    @ApiOperation(value = "Remove product from basket")
    public ResponseEntity<ProductResource> removeProduct(@ApiParam(value = "Basket Identifier", required = true) @PathVariable("basketId") Long basketId,
                                                         @ApiParam(value = "Product resource", required = true) @RequestBody ProductDTO productDTO){
        logger.trace("Received remove product request for {}", productDTO.getTitle());

        boolean result = basketService.removeProduct(basketId, productDTO);

        if(result){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    //get basket
    @GetMapping(path = "{basketId}/customer/{customerId}")
    @ApiOperation(value = "Get basket by customer id")
    public ResponseEntity<BasketResource> getBasket(@ApiParam(value = "Basket Identifier", required = true) @PathVariable("basketId") Long basketId,
                                                    @ApiParam(value = "Basket Id", required = true) @PathVariable("customerId") Long customerId){
        logger.trace("Received get basket request for basketId : {} and customerId : {}", basketId, customerId);

        BasketResource basketResource = basketService.getBasket(basketId, customerId);

        if(basketResource == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(basketResource);
    }

    //add cargo option
    @PostMapping(path = "{basketId}/cargo")
    @ApiOperation(value = "Add cargo option to the basket")
    public ResponseEntity<CargoOptionResource> addCargoOption(@ApiParam(value = "Basket Id", required = true) @PathVariable("basketId") Long basketId,
                                                              @ApiParam(value = "Cargo Resource", required = true) @RequestBody CargoOptionDTO cargoOptionDTO) {
        logger.trace("Cargo option addition request is received for basket with basketId : {}", basketId);

        boolean result = basketService.addCargoOption(basketId, cargoOptionDTO);

        if(result){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}

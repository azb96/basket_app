package com.example.basket.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"productSum","cargoPrice","totalPrice"})
public class BasketInfo implements Serializable {

    @JsonProperty("Ara Toplam")
    private Double productSum;

    @JsonProperty("Kargo")
    private Double cargoPrice;

    @JsonProperty("Toplam")
    private Double totalPrice;

    public Double getProductSum() {
        return productSum;
    }

    public void setProductSum(Double productSum) {
        this.productSum = productSum;
    }

    public Double getCargoPrice() {
        return cargoPrice;
    }

    public void setCargoPrice(Double cargoPrice) {
        this.cargoPrice = cargoPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

package com.example.basket.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class BasketResource implements Serializable {
    private static final long serialVersionUID = -1451448999611432894L;
    @JsonProperty("products")
    private List<ProductResource> productResourceList;
    @JsonProperty("basketInfo")
    private BasketInfo basketInfo;

    public List<ProductResource> getProductResourceList() {
        return productResourceList;
    }

    public void setProductResourceList(List<ProductResource> productResourceList) {
        this.productResourceList = productResourceList;
    }

    public BasketInfo getBasketInfo() {
        return basketInfo;
    }

    public void setBasketInfo(BasketInfo basketInfo) {
        this.basketInfo = basketInfo;
    }
}

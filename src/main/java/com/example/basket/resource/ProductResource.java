package com.example.basket.resource;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
@JsonPropertyOrder({"id","image","title","quantity","price"})
public class ProductResource implements Serializable {

    private Long id;
    private String image;
    private String title;
    private Integer quantity;
    private Double price;

    public ProductResource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

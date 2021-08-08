package com.example.basket.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "basket")
@AllArgsConstructor
@NoArgsConstructor
public class Basket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long customerId;

    @Column
    private String customerEmail;

    @OneToMany(mappedBy = "basket")
    private Set<BasketItem> basketItemSet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargoOption_id", referencedColumnName = "id")
    private CargoOption cargoOption;

    public Basket(Long customerId, String customerEmail) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Set<BasketItem> getBasketItemSet() {
        return basketItemSet;
    }

    public void setBasketItemSet(Set<BasketItem> basketItemSet) {
        this.basketItemSet = basketItemSet;
    }

    public CargoOption getCargoOption() {
        return cargoOption;
    }

    public void setCargoOption(CargoOption cargoOption) {
        this.cargoOption = cargoOption;
    }
}

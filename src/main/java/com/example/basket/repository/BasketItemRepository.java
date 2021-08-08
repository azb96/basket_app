package com.example.basket.repository;

import com.example.basket.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    List<BasketItem> findByProductId(Long productId);
}

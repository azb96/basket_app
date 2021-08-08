package com.example.basket.service.basket;

import com.example.basket.dto.CargoOptionDTO;
import com.example.basket.dto.ProductDTO;
import com.example.basket.entity.Basket;
import com.example.basket.entity.BasketItem;
import com.example.basket.entity.CargoOption;
import com.example.basket.repository.BasketItemRepository;
import com.example.basket.repository.BasketRepository;
import com.example.basket.repository.CargoOptionRepository;
import com.example.basket.resource.BasketInfo;
import com.example.basket.resource.BasketResource;
import com.example.basket.resource.ProductResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BasketServiceImpl implements BasketService {
    Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final CargoOptionRepository cargoOptionRepository;

    @Autowired
    public BasketServiceImpl(final BasketRepository basketRepository,
                             final BasketItemRepository basketItemRepository,
                             final CargoOptionRepository cargoOptionRepository){
        this.basketRepository = basketRepository;
        this.basketItemRepository = basketItemRepository;
        this.cargoOptionRepository = cargoOptionRepository;
    }

//    @Override
//    public ProductResource addProduct(Long basketId, ProductDTO productDTO) {
//        Optional<Basket> basket = basketRepository.findById(basketId);
//        if(basket.isPresent()){
//
//            BasketItem basketItem =
//                    new BasketItem(productDTO.getId(), productDTO.getImage(), productDTO.getTitle(), productDTO.getQuantity(), productDTO.getPrice());
//
//            basket.get().getBasketItemSet().add(basketItem);
//        }else{
//
//        }
//
//        return productResource;
//    }

    @Override
    public boolean removeProduct(Long basketId, ProductDTO productDTO) {
        Optional<Basket> basket = basketRepository.findById(basketId);

        if(basket.isPresent()){
            BasketItem productToAdd = convertProductDTOToBasketItem(productDTO);

            Set<BasketItem> existingProducts = basket.get().getBasketItemSet();
            if(!existingProducts.contains(productToAdd)){
                productToAdd.setBasket(basket.get());
                existingProducts.add(productToAdd);

                basketItemRepository.save(productToAdd);
            }else{
                for(BasketItem existingProduct : existingProducts){
                    if(existingProduct.equals(productToAdd)){
                        existingProduct.setQuantity(existingProduct.getQuantity() + productToAdd.getQuantity());
                    }
                }
            }
            basketRepository.save(basket.get());
        }else{
            return false;
        }
        return true;
    }

    @Override
    public boolean addCargoOption(Long basketId, CargoOptionDTO cargoOptionDTO) {
        Optional<Basket> basket = basketRepository.findById(basketId);
        if(basket.isPresent()){
            CargoOption cargoOption = convertCargoOptionDtoToCargoOption(cargoOptionDTO);
            cargoOption.setBasket(basket.get());
            basket.get().setCargoOption(cargoOption);

            cargoOptionRepository.save(cargoOption);
            basketRepository.save(basket.get());
        }else{
            return false;
        }
        return true;
    }


    @Override
    public BasketResource getBasket(Long basketId, Long customerId) {

        Optional<Basket> basket = basketRepository.findById(basketId);
        return basket.map(this::convertBasketToResource).orElse(null);
    }

    public BasketResource convertBasketToResource(Basket basket){
        BasketResource basketResource = new BasketResource();
        BasketInfo basketInfo = new BasketInfo();
        List<ProductResource> productResources = new ArrayList<>();
        Double productSum = 0.0;

        for (BasketItem basketItem: basket.getBasketItemSet()) {
            productSum += basketItem.getTotalPriceForItem();
            productResources.add(convertBasketItemToProductResource(basketItem));
        }

        basketInfo.setCargoPrice(basket.getCargoOption().getServicePrice());
        basketInfo.setProductSum(productSum);
        basketInfo.setTotalPrice(basketInfo.getCargoPrice() + basketInfo.getProductSum());

        basketResource.setProductResourceList(productResources);
        basketResource.setBasketInfo(basketInfo);

        return basketResource;
    }

    public ProductResource convertBasketItemToProductResource(BasketItem basketItem){
        ProductResource productResource = new ProductResource();

        productResource.setId(basketItem.getProductId());
        productResource.setImage(basketItem.getImage());
        productResource.setPrice(basketItem.getPrice());
        productResource.setQuantity(basketItem.getQuantity());
        productResource.setTitle(basketItem.getTitle());

        return productResource;
    }

    public BasketItem convertProductDTOToBasketItem(ProductDTO productDTO){
        BasketItem basketItem = new BasketItem();

        basketItem.setImage(productDTO.getImage());
        basketItem.setPrice(productDTO.getPrice());
        basketItem.setProductId(productDTO.getId());
        basketItem.setQuantity(productDTO.getQuantity());
        basketItem.setTitle(productDTO.getTitle());

        return basketItem;
    }

    public CargoOption convertCargoOptionDtoToCargoOption(CargoOptionDTO cargoOptionDTO){
        CargoOption cargoOption = new CargoOption();

        cargoOption.setCompanyName(cargoOptionDTO.getCompanyName());
        cargoOption.setServicePrice(cargoOptionDTO.getServicePrice());

        return cargoOption;
    }
}

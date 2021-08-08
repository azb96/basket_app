package com.example.basket.amqp;


import com.example.basket.amqp.Message.Message;
import com.example.basket.service.basket.BasketService;
import com.example.basket.service.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final BasketService subscriptionService;
    private final NotificationService notificationService;

    @Autowired
    public MessageConsumer(final BasketService subscriptionService,
                           final NotificationService notificationService) {
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "stock-change-info-queue")
    public void handleStockChange(Message message){
        logger.trace("Stock change info is received. For product with product id : {}", message.getMessageContent().getProductId());

    }

    @RabbitListener(queues = "stock-empty-info-queue")
    public void handleStockDepletion(Message message){
        logger.trace("Stock depletion info is received. For product with product id : {}", message.getMessageContent().getProductId());

    }

    @RabbitListener(queues = "price-change-info-queue")
    public void handlePriceChange(Message message){
        logger.trace("Price change info is received. For product with product id : {}", message.getMessageContent().getProductId());

    }


}
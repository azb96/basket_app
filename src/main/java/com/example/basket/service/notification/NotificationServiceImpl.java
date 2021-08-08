package com.example.basket.service.notification;

import com.example.basket.amqp.Message.MessageContent;
import com.example.basket.constant.BasketServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    private static final String NOREPLY_ADDRESS = "noreply@samplebasket.com";

    private final JavaMailSender mailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(List<String> emails, String notificationType, MessageContent messageContent) {
        String message = "";
        switch (notificationType){
            case BasketServiceConstants.STOCK_CHANGE:
                message = "Stock of the product " + messageContent.getProductName() + " is less than 3";
                break;
            case BasketServiceConstants.STOCK_DEPLETED:
                message = "Stock of the product " + messageContent.getProductName() + " is depleted";
                break;
            case BasketServiceConstants.PRICE_CHANGE:
                message = "Discount happened for product " + messageContent.getProductName() + ". Current price : " +messageContent.getProductPrice();
                break;
            default:
                throw new IllegalStateException("Unexpected notification type");
        }

        for(String email : emails){
            sendEmail(email, message);
        }
    }


    public void sendEmail(String email, String notificationMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NOREPLY_ADDRESS);
        message.setTo(email);
        message.setSubject("Notification");
        message.setText(notificationMessage);
        mailSender.send(message);
    }

}

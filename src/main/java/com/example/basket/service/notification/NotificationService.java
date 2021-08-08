package com.example.basket.service.notification;

import com.example.basket.amqp.Message.MessageContent;

import java.util.List;

public interface NotificationService {
    public void sendNotification(List<String> emails, String notificationType, MessageContent messageContent);
}

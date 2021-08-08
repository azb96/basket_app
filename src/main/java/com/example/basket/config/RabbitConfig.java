package com.example.basket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    @Value("${sr.rabbit.queue.name.stock.change}")
    private String stockChangeQueueName;
    @Value("${sr.rabbit.queue.name.stock.depletion}")
    private String stockDepletionQueueName;
    @Value("${sr.rabbit.queue.name.price.change}")
    private String priceChangeQueueName;


    @Value("${sr.rabbit.routing.stock.change}")
    private String stockChangeRouting;
    @Value("${sr.rabbit.routing.name.stock.depletion}")
    private String stockDepletionRouting;
    @Value("${sr.rabbit.routing.name.price.change}")
    private String priceChangeRouting;

    @Value("${sr.rabbit.routing.basket}")
    private String exchangeName;


    @Bean
    public Queue queue1() {
        return new Queue(stockChangeQueueName, true);
    }
    @Bean
    public Queue queue2() {
        return new Queue(stockDepletionQueueName, true);
    }
    @Bean
    public Queue queue3() {
        return new Queue(priceChangeQueueName, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding1(@Qualifier("queue1") Queue queue, final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(stockChangeRouting);
    }
    @Bean
    public Binding binding2(@Qualifier("queue2") Queue queue, final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(stockDepletionRouting);
    }
    @Bean
    public Binding binding3(@Qualifier("queue3") Queue queue, final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(priceChangeRouting);
    }


    // for parsing received JSON messages
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    // for converting sent messages to JSON
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}

package com.java06.luxurious_hotel.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    final static public String CONFIRM_EMAIL_QUEUE = "confirm-email-queue";
    final static public String CONFIRM_EMAIL_EXCHANGE = "confirm-email-exchange";
    final static public String CONFIRM_EMAIL_ROUTING_KEY = "/confirm-email";


    @Bean
    public Queue queueConfirmEmail()
    {
        return new Queue(CONFIRM_EMAIL_QUEUE, true);
    }

    @Bean public Exchange exchangeConfirmEmail()
    {
        return new TopicExchange(CONFIRM_EMAIL_EXCHANGE);
    }

    @Bean
    public Binding binding()
    {
        return BindingBuilder.bind(queueConfirmEmail())
                .to(exchangeConfirmEmail())
                .with(CONFIRM_EMAIL_ROUTING_KEY)
                .noargs();
    }
}

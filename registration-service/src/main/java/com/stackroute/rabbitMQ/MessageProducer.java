package com.stackroute.rabbitMQ;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }




    public void sendMessageToAuthenticationService(AuthenticationUserDTO authenticationUserDTO)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"key1", authenticationUserDTO);
    }



}
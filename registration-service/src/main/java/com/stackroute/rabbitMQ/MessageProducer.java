package com.stackroute.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        log.debug("Carnivry User email id {} and password sent to Authentication MicroService using AuthenticationUserDTO"
                ,authenticationUserDTO.getEmail());
    }


    //send new user data to Suggestion service
    public void sendMessageToSuggestionService(SuggestionUserDTO suggestionUserDTO)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"key3", suggestionUserDTO);
        log.debug("Details of Carnivry User with email id {}  sent to Authentication MicroService using SuggestionUserDTO"
                ,suggestionUserDTO.getEmailId());
    }


}

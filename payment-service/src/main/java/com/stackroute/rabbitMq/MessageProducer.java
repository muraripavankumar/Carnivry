package com.stackroute.rabbitMq;

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

    public void sendMessageToRegistrationService(RegistrationServiceDTO registrationServiceDTO)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"key11", registrationServiceDTO);
        log.debug("Ticket booked by Carnivry User having email id {} is sent to Registration MicroService"
                ,registrationServiceDTO.getEmail());
    }



    public void sendMessageToNotificationService(NotificationServiceDTO notificationServiceDTO)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"key12", notificationServiceDTO);
        log.debug("Ticket details booked by Carnivry User having email id {} is sent to Notification MicroService"
                ,notificationServiceDTO.getEmail());
    }


}
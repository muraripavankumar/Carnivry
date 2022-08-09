package com.stackroute.rabbitMQ;

import com.stackroute.model.Event;
import com.stackroute.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final UserService userService;

    @Autowired
    public MessageConsumer(UserService userService) {
        this.userService = userService;
    }

    //old bought ticket
//    @RabbitListener(queues = "queue_5")
    public void addbookedTickets(Event pastEvent){
        userService.saveBookedTickets(pastEvent);
    }

}

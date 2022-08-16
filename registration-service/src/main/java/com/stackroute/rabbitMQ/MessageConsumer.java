package com.stackroute.rabbitMQ;

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
//    @RabbitListener(queues = "payment_registration_queue")

    public void addbookedTickets(TicketDTO ticket){
        userService.saveBookedTickets(ticket);
    }


}

package com.stackroute.rabbitmq;

import com.stackroute.Services.EventService;
import com.stackroute.entity.Events;
import com.stackroute.exception.EventAlreadyExistException;
import com.stackroute.exception.EventNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private EventService eventService;
    @Autowired
    Consumer(EventService eventService){
        this.eventService=eventService;
    }

    @RabbitListener(queues = "management_suggestion_queue")
    public void getDataAndAddEvents(Events events) throws EventAlreadyExistException {
        eventService.addEvents(events);
    }

//    @RabbitListener(queues = "management_suggestion_update_event_queue")
//    public void getDataAndUpdateEvents(Events events) throws EventAlreadyExistException, EventNotFoundException {
//        eventService.updateEvent(events);
//    }
}

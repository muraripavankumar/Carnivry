package com.stackroute.rabbitmq;

import com.stackroute.model.Event;
import com.stackroute.modelDTO.EventDTO;
import com.stackroute.modelDTO.EventSuggestionsDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Producer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }
    //sending event data to notification service
    public void sendMessageToMq(EventDTO eventDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(), "user_routing", eventDTO);
    }
    //sending data for new event to Suggestion service
    public void sendEventToMq(EventSuggestionsDTO eventSuggestionsDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),"management_routing",eventSuggestionsDTO);
    }
    //sending updated event data to Suggestion service
    public void sendUpdatedEventToMq(EventSuggestionsDTO eventSuggestionsDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),"management_update_event_routing",eventSuggestionsDTO);
    }

}

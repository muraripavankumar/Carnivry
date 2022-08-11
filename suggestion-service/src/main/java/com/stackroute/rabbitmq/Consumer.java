package com.stackroute.rabbitmq;


import com.stackroute.Services.EventService;
import com.stackroute.Services.UserService;
import com.stackroute.entity.Events;
import com.stackroute.entity.User;
import com.stackroute.exception.EventAlreadyExistException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserAlreadyExistException;
import com.stackroute.model.EventDTO;
import com.stackroute.model.SuggestionUserDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Consumer {
    private EventService eventService;
    private UserService userService;
    @Autowired
    Consumer(EventService eventService, UserService userService){
        this.eventService=eventService;
        this.userService=userService;
    }

    @RabbitListener(queues = "management_suggestion_queue")
    public void getDataAndAddEvents(EventDTO eventDTO) throws EventAlreadyExistException {
        eventService.addEvents(eventDTO);
    }

    @RabbitListener(queues = "management_suggestion_update_event_queue")
    public void getDataAndUpdateEvents(EventDTO eventDTO) throws EventAlreadyExistException, EventNotFoundException {
        eventService.updateEvent(eventDTO);
    }

    @RabbitListener(queues="registration_suggestion_queue")
    public void getNewUserAddToDB(SuggestionUserDTO suggestionUserDTO) throws UserAlreadyExistException {
        User user=new User();
        user.setEmailId(suggestionUserDTO.getEmailId());
        user.setName(suggestionUserDTO.getName());
        user.setWishlist(suggestionUserDTO.getWishlist());
        user.setCity(suggestionUserDTO.getCity());
        List<String> genreName = new ArrayList<>();
        suggestionUserDTO.getLikedGenre().forEach(g-> genreName.add(g.toString()));
        user.setLikedGenre(genreName);
        userService.addUser(user);
    }
//    @RabbitListener(queues = "registration_suggestion_update_user_queue")
//    public void getUpdateUserUpdateToDB(User user){
//
//    }
}

package com.example.SuggestionService.Services;

import com.example.SuggestionService.Respository.EventsRepo;
import com.example.SuggestionService.Respository.UserRepo;
import com.example.SuggestionService.entity.Events;
import com.example.SuggestionService.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

@Service
public class EventService {

    @Autowired
    EventsRepo eventsRepo;
    @Autowired
    UserRepo userRepo;

    public EventService(EventsRepo eventsRepo, UserRepo userRepo) {
        this.eventsRepo = eventsRepo;
        this.userRepo = userRepo;
    }

    //add new event
    public Events addEvents(Events events){

//        Events event=new ObjectMapper().readValue(eventText,Events.class);
//        event.setPoster(poster.getBytes());
//        System.out.println("EventText: "+eventText);
//        System.out.println("Event poster: "+event.getPoster());
//        System.out.println("Poster: "+poster);
        eventsRepo.save(events);

        //get all users
        List<User> userList= userRepo.findAll();

        ListIterator<User> iterator= userList.listIterator();
        //connect events with the users have same genre
        while (iterator.hasNext()){
            User user= iterator.next();
            for(int i=0; i<events.getGenre().size(); i++) {
                if (user.getLikedGenre().contains(events.getGenre().get(i))) {
                    eventsRepo.connectWithUser(user.getName(),events.getTitle());
                    System.out.println("Users matching the event genre: " + user.getName());
                    break;
                }
            }
        }
        System.out.println("Event Added successfully");
        return events;
    }


    //update event
    public Events updateEvent(Events events){
        if(eventsRepo.findById(events.getEventId()).isPresent()){
            eventsRepo.save(events);
        }
        return events;
    }

    //update likes of the event
    public String updateEventLikes(String emailId, String eventId){

        String response="";

        Events events=eventsRepo.findById(eventId).get();

        if(events.getEmailOfUsersLikedEvent().contains(emailId)){
            System.out.println("You have already liked this event");
            response="You have already liked this event";
        }
        else{
            int totalLikes= events.getLikes();
            totalLikes++;
            events.setLikes(totalLikes);
            List<String> allEmailIds= events.getEmailOfUsersLikedEvent();
            allEmailIds.add(emailId);
            events.setEmailOfUsersLikedEvent(allEmailIds);
            eventsRepo.save(events);
            System.out.println("Likes upadated and total likes are :"+events.getLikes());
            response="Likes upadated";
        }
        return response;
    }

    //get all events
    public List<Events> getAllEvents(){
        List<Events> eventsList=eventsRepo.findAll();
        System.out.println("All Events: "+eventsList);
        return eventsList;
    }

}

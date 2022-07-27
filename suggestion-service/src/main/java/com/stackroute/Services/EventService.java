package com.example.SuggestionService.Services;

import com.example.SuggestionService.Respository.EventsRepo;
import com.example.SuggestionService.Respository.UserRepo;
import com.example.SuggestionService.entity.Events;
import com.example.SuggestionService.entity.User;
import com.example.SuggestionService.exception.EventAlreadyExistException;
import com.example.SuggestionService.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

@Service
public class EventService {


    EventsRepo eventsRepo;
    UserRepo userRepo;

    @Autowired
    public EventService(EventsRepo eventsRepo, UserRepo userRepo) {
        this.eventsRepo = eventsRepo;
        this.userRepo = userRepo;
    }

    Logger logger
            = Logger.getLogger(
            UserService.class.getName());

    //add new event
    public Events addEvents(Events events) throws EventAlreadyExistException {
        //saving the event in repository
        if(eventsRepo.findById(events.getEventId()).isPresent()){
            logger.info("The event with this id already exists");
            throw new EventAlreadyExistException();
        }
        else {
            eventsRepo.save(events);

            //get all users
            List<User> userList = userRepo.findAll();

            ListIterator<User> iterator = userList.listIterator();
            //connect events with the users have same genre
            while (iterator.hasNext()) {
                User user = iterator.next();
                for (int i = 0; i < events.getGenre().size(); i++) {
                    if (user.getLikedGenre().contains(events.getGenre().get(i))) {
                        eventsRepo.connectWithUser(user.getName(), events.getTitle());
                        logger.info("Users matching the event genre: " + user.getName());
                        break;
                    }
                }
            }
            logger.info("Event Added successfully");
        }
        return events;
    }


    //update event
    public Events updateEvent(Events events) throws EventNotFoundException {
        if(eventsRepo.findById(events.getEventId()).isPresent()){
            eventsRepo.save(events);
        }
        else{
            throw new EventNotFoundException();
        }
        return events;
    }

    //update likes of the event
    public String updateEventLikes(String emailId, String eventId) throws Exception {

        String response="";

        Events events=eventsRepo.findById(eventId).get();

        if(events.getEmailOfUsersLikedEvent().contains(emailId)){
            response="You have already liked this event";
            throw new Exception();
        }
        else{
            int totalLikes= events.getLikes();
            totalLikes++;
            events.setLikes(totalLikes);
            List<String> allEmailIds= events.getEmailOfUsersLikedEvent();
            allEmailIds.add(emailId);
            events.setEmailOfUsersLikedEvent(allEmailIds);
            eventsRepo.save(events);
            logger.info("Likes upadated and total likes are :"+events.getLikes());
            response="Likes upadated";
        }
        return response;
    }

    //get all events
    public List<Events> getAllEvents() throws EventNotFoundException {
        List<Events> eventsList=eventsRepo.findAll();
        if(eventsList.isEmpty()){
            throw new EventNotFoundException();
        }
        else {
            logger.info("All Events: " + eventsList);
        }
        return eventsList;
    }

}

package com.stackroute.Services;

import com.stackroute.Respository.EventsRepo;
import com.stackroute.Respository.UserRepo;
import com.stackroute.entity.Events;
import com.stackroute.entity.User;
import com.stackroute.exception.EventAlreadyExistException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Events addEvents(EventDTO eventDTO) throws EventAlreadyExistException {
        Events events=new Events();
        events.setEventId(eventDTO.getEventId());
        events.setTitle(eventDTO.getTitle());
        events.setEventType(eventDTO.getEventType());
        events.setUserEmailId(eventDTO.getUserEmailId());
        events.setGenre(eventDTO.getGenre());
        events.setLanguages(eventDTO.getLanguages());
        events.setStartDate(eventDTO.getStartDate());
        events.setEndDate(eventDTO.getEndDate());
        events.setPoster(eventDTO.getPoster());
        events.setCity(eventDTO.getCity());
        events.setTicketsSold(0);
        events.setRevenueGenerated(0.0);
        events.setPrice(eventDTO.getPrice());
        events.setTotalSeats(eventDTO.getTotalSeats());
        events.setLikes(0);
        events.setEmailOfUsersLikedEvent(new ArrayList<>());

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
    public Events updateEvent(EventDTO eventDTO) throws EventNotFoundException {
        Events events = new Events();
        if(eventsRepo.findById(eventDTO.getEventId()).isPresent()){
            events=eventsRepo.findById(eventDTO.getEventId()).get();
//            events.setEventId(eventDTO.getEventId());
            events.setTitle(eventDTO.getTitle());
            events.setEventType(eventDTO.getEventType());
            events.setUserEmailId(eventDTO.getUserEmailId());
            events.setGenre(eventDTO.getGenre());
            events.setLanguages(eventDTO.getLanguages());
            events.setStartDate(eventDTO.getStartDate());
            events.setEndDate(eventDTO.getEndDate());
            events.setPoster(eventDTO.getPoster());
            events.setCity(eventDTO.getCity());
//            events.setTicketsSold(0);
//            events.setRevenueGenerated(0.0);
            events.setPrice(eventDTO.getPrice());
            events.setTotalSeats(eventDTO.getTotalSeats());
//            events.setLikes(0);
//            events.setEmailOfUsersLikedEvent(new ArrayList<>());

            eventsRepo.save(events);
        }
        else{
            throw new EventNotFoundException();
        }
        return events;
    }

    //update likes of the event
    public Events updateEventLikes(String emailId, String eventId) throws Exception {

        String response="";
        Events events=eventsRepo.findById(eventId).get();;

        if(emailId==null){
            response="Please log in before liking an event";
        }

        else {
            if (events.getEmailOfUsersLikedEvent().contains(emailId)) {
                response = "You have already liked this event";
                throw new Exception();
            } else {
                int totalLikes = events.getLikes();
                totalLikes++;
                events.setLikes(totalLikes);
                List<String> allEmailIds = events.getEmailOfUsersLikedEvent();
                allEmailIds.add(emailId);
                events.setEmailOfUsersLikedEvent(allEmailIds);
                eventsRepo.save(events);
                logger.info("Likes upadated and total likes are :" + events.getLikes());
                response = "Likes upadated";
            }
        }
        return events;
    }


    //get all events
    public List<Events> getAllEvents() throws EventNotFoundException {
        List<Events> eventsList=eventsRepo.findAll();
        if(eventsList.isEmpty()){
            throw new EventNotFoundException();
        }
        else {
            logger.info("All Events: " + eventsList.size());
        }
        return eventsList;
    }

}

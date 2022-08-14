package com.stackroute.service;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.Event;
import com.stackroute.model.Seat;
import com.stackroute.modelDTO.EventDTO;
import com.stackroute.modelDTO.EventSuggestionsDTO;
import com.stackroute.rabbitmq.Producer;
import com.stackroute.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private Producer producer;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository,Producer producer) {
        this.eventRepository = eventRepository;
        this.producer=producer;
        log.info("Constructing EventServieceImpl ");
    }

    //method to add a new event to event repository.
    @Override
    public boolean addEvent(Event event) throws EventAlreadyExistsException,Exception {

        event.setEmailOfUsersLikedEvent(new ArrayList<>());
        event.setEventId(String.valueOf(UUID.randomUUID()));
        if(eventRepository.findById(event.getEventId()).isEmpty()) {
            eventRepository.insert(event);
            EventDTO eventDTO=new EventDTO(
                    event.getUserEmailId(),
                    event.getTitle(),
                    event.getUserName(),
                    event.getEventTimings(),
                    event.getVenue(),
                    event.getTotalSeats());
            BigDecimal minPrice=findMinPrice(event.getSeats());
            EventSuggestionsDTO eventSuggestionsDTO= new EventSuggestionsDTO(
                    event.getEventId(),
                    event.getTitle(),
                    eventType(event),
                    event.getUserEmailId(),
                    event.getGenre(),
                    event.getLanguages(),
                    event.getEventTimings().getStartDate(),
                    event.getEventTimings().getEndDate(),
                    event.getPosters().get(0),
                    event.getVenue().getAddress().getCity(),
                    event.getTicketsSold(),
                    minPrice.doubleValue(),
                    event.getTotalSeats()
                    );
            producer.sendMessageToMq(eventDTO);
            producer.sendEventToMq(eventSuggestionsDTO);
            return true;
        }
        else {
            log.error("EventAlreadyExists occurred in EventServiceImpl-> addEvent() ");
            throw new EventAlreadyExistsException();
        }

    }
    private BigDecimal findMinPrice(List<Seat> seatList){
       List<Seat> resultList= seatList.stream().sorted((s1,s2)-> s1.getSeatPrice().compareTo(s2.getSeatPrice())).collect(Collectors.toList());
        return resultList.get(0).getSeatPrice();
    }
    private String eventType(Event event){
        if(event.getVenue().getAddress().getStreet().equals("-NA-"))
            return "ONLINE";
        return "OFFLINE";

    }

    //method to update an existing event with new data.
    @Override
    public boolean updateEvent(Event event) throws EventNotFoundException,Exception {
        if(eventRepository.findById(event.getEventId()).isPresent()){
            eventRepository.save(event);
            BigDecimal minPrice=findMinPrice(event.getSeats());
            EventSuggestionsDTO eventSuggestionsDTO= new EventSuggestionsDTO(
                    event.getEventId(),
                    event.getTitle(),
                    eventType(event),
                    event.getUserEmailId(),
                    event.getGenre(),
                    event.getLanguages(),
                    event.getEventTimings().getStartDate(),
                    event.getEventTimings().getEndDate(),
                    event.getPosters().get(0),
                    event.getVenue().getAddress().getCity(),
                    event.getTicketsSold(),
                    minPrice.doubleValue(),
                    event.getTotalSeats()
            );
            producer.sendUpdatedEventToMq(eventSuggestionsDTO);
            return true;
        }
        else {
            log.error("EventNotFoundException occurred in EventServiceImpl-> updateEvent() ");
            throw new EventNotFoundException();
        }
    }

    //method to search an event by its eventId and retrive the event object.
    @Override
    public Event getEventById(String eventId) throws EventNotFoundException,Exception {
        return eventRepository.findById(eventId).orElseThrow(() ->{
            log.error("EventNotFoundException occurred in EventServiceImpl-> getEventById()");
            return new EventNotFoundException();});
    }
    //method to retrieve all events
    @Override
    public List<Event> getAllEvents()throws Exception{
        return eventRepository.findAll();
    }
    //method to retrieve all events posted by a particular user
    @Override
    public List<Event> getAllEventsByUserEmailId( String userEmail) throws UserNotFoundException, Exception {
        List<Event> result=eventRepository.findByUserEmailId(userEmail);
        if(result.size()>0){
            return result;
        } else {
            log.error("UserNotFoundException occurred in EventService -> getAllEventsByUserEmailId()");
            throw new  UserNotFoundException();
        }
    }

    @Override
    public List<Event> getPastEventsByUserEmailId(String userEmail) throws UserNotFoundException, Exception {
        List<Event> allEvents=getAllEventsByUserEmailId(userEmail);
        Date today=new Date();
        return allEvents.stream().filter(event -> event.getEventTimings().getEndDate().compareTo(today)<0).collect(Collectors.toList());
    }

    @Override
    public List<Event> getUpcomingEventsByUserEmailId(String userEmail) throws UserNotFoundException, Exception {
        List<Event> allEvents=getAllEventsByUserEmailId(userEmail);
        Date today=new Date();
        return allEvents.stream().filter(event -> event.getEventTimings().getEndDate().compareTo(today)>=0).collect(Collectors.toList());
    }

    @Override
    public boolean updateNoOfLikes(String eventId, boolean flag) throws EventNotFoundException {
        if(eventRepository.findById(eventId).isPresent()){
        Event result=eventRepository.findById(eventId).get();
        //if flag is true, increment the number of likes by 1
        if(flag){
            result.setLikes(result.getLikes()+1);
            eventRepository.save(result);
            return true;
        }
        //if flag is false, decrement the number of likes by 1
        else {
            result.setLikes(result.getLikes()-1);
            eventRepository.save(result);
            return true;
        }}
        throw new EventNotFoundException();
    }
}

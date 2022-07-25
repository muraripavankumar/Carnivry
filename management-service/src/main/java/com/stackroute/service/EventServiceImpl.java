package com.stackroute.service;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;
import com.stackroute.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        log.info("Constructing EventServieceImpl ");
    }

    //method to add a new event to event repository.
    @Override
    public boolean addEvent(Event event) throws EventAlreadyExistsException,Exception {
        event.setEmailOfUsersLikedEvent(new ArrayList<>());
        event.setEventId(String.valueOf(UUID.randomUUID()));
        if(eventRepository.findById(event.getEventId()).isEmpty()) {
            eventRepository.insert(event);
            return true;
        }
        else {
            log.error("EventAlreadyExists occurred in EventServiceImpl-> addEvent() ");
            throw new EventAlreadyExistsException();
        }

    }

    //method to update an existin event with new data.
    @Override
    public boolean updateEvent(Event event) throws EventNotFoundException,Exception {
        if(eventRepository.findById(event.getEventId()).isPresent()){
            eventRepository.save(event);
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

    @Override
    public List<Event> getAllEvents()throws Exception{
        return eventRepository.findAll();
    }
}

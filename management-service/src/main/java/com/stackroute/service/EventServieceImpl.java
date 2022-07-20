package com.stackroute.service;

import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;
import com.stackroute.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventServieceImpl implements EventService{
    @Autowired
    private EventRepository eventRepository;
    @Override
    public boolean addEvent(Event event) {
        event.setEmailOfUsersLikedEvent(new ArrayList<>());
       eventRepository.save(event);
         return true;
    }

    @Override
    public boolean updateEvent(Event event) throws EventNotFoundException {
        if(eventRepository.findById(event.getEventId()).isPresent()){
            eventRepository.save(event);
            return true;
        }
        throw new EventNotFoundException();
    }

    @Override
    public Event getEventById(String eventId) throws Exception {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }
}

package com.stackroute.service;

import com.stackroute.model.Event;
import com.stackroute.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServieceImpl implements EventService{
    @Autowired
    private EventRepository eventRepository;
    @Override
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }
}

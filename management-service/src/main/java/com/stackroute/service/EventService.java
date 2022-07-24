package com.stackroute.service;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;

import java.util.List;

public interface EventService {
    boolean addEvent(Event event)throws EventAlreadyExistsException,Exception;
    boolean updateEvent(Event event) throws EventNotFoundException,Exception;
    Event getEventById(String eventId) throws Exception;
    List<Event> getAllEvents();
}

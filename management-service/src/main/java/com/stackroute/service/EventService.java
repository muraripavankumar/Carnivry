package com.stackroute.service;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;

public interface EventService {
    boolean addEvent(Event event)throws EventAlreadyExistsException;
    boolean updateEvent(Event event) throws EventNotFoundException;
    Event getEventById(String eventId) throws Exception;
}

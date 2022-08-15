package com.stackroute.service;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.Event;

import java.util.List;

public interface EventService {
    boolean addEvent(Event event)throws EventAlreadyExistsException,Exception;
    boolean updateEvent(Event event) throws EventNotFoundException,Exception;
    Event getEventById(String eventId) throws Exception;
    List<Event> getAllEvents()throws Exception;
    List<Event> getAllEventsByUserEmailId(String userEmail) throws UserNotFoundException, Exception;
    List<Event> getPastEventsByUserEmailId(String userEmail) throws UserNotFoundException, Exception;
    List<Event> getUpcomingEventsByUserEmailId(String userEmail) throws UserNotFoundException, Exception;
    boolean updateNoOfLikes(String eventId,boolean flag) throws EventNotFoundException;
}

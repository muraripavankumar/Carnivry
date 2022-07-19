package com.stackroute.service;

import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EventService {
    boolean addEvent(Event event);

    boolean updateEvent(Event event) throws EventNotFoundException;

    Event getEventById(String eventId) throws Exception;
}

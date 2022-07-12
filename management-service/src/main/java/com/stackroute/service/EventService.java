package com.stackroute.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.model.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EventService {
    Event addEvent(String eventText, MultipartFile posterPic) throws IOException;

    Event updateEvent(Event event);
}

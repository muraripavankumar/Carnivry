package com.stackroute.service;

import com.stackroute.model.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EventService {
    boolean addEvent(String eventText, MultipartFile posterPic) throws IOException;
    Event updateEvent(String eventText, MultipartFile posterPic) throws IOException;
    Event getEventById(String eventId) throws Exception;
}

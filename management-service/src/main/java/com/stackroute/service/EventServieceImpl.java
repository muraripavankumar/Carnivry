package com.stackroute.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.model.Event;
import com.stackroute.repository.EventRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventServieceImpl implements EventService{
    @Autowired
    private EventRepository eventRepository;
    @Override
    public Event addEvent(String eventText, MultipartFile posterPic) throws IOException {
        //convert Json text to Event Object
        Event event=new ObjectMapper().readValue(eventText,Event.class);
        String filename= StringUtils.cleanPath(Objects.requireNonNull(posterPic.getOriginalFilename()));
        //generating an UUTD and saving in form of String
        event.setEventId(String.valueOf(UUID.randomUUID()));
        event.setPoster(new Binary(BsonBinarySubType.BINARY, posterPic.getBytes()));
        event.setFileName(filename);
        event.setFileType(posterPic.getContentType());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(String eventText, MultipartFile posterPic) throws IOException {
        Event event=new ObjectMapper().readValue(eventText,Event.class);
        String filename=StringUtils.cleanPath(Objects.requireNonNull(posterPic.getOriginalFilename()));
        event.setPoster(new Binary(BsonBinarySubType.BINARY, posterPic.getBytes()));
        event.setFileName(filename);
        event.setFileType(posterPic.getContentType());
        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(String eventId) throws Exception {
        return eventRepository.findById(eventId).orElseThrow(()->new Exception("Event not found"));
    }
}

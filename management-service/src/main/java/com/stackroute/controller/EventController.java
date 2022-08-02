package com.stackroute.controller;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;
import com.stackroute.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class EventController {
    //addEvent               = POST =
    //updateEvent            = PATCH =
    //getAllEvents           = GET
    //getEventByEventId      = GET = /{eventId}


    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
        log.info("Constructing EventController");
    }

    //method to accept HTTP POST request to add new event.
    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event)throws EventAlreadyExistsException,Exception {
        try {
            return new ResponseEntity<>(eventService.addEvent(event), HttpStatus.CREATED) ;
        }catch (EventAlreadyExistsException eaee){
            log.error("EventAlreadyExistsException occurred in EventController -> addEvent()");
            return new ResponseEntity<>("Sorry for inconvenience! We will be back soon.",HttpStatus.CONFLICT);
        }
        catch (Exception e){
            log.error("Exception occurred in EventController -> addEvent() ");
            return new ResponseEntity<>("Sorry for inconvenience! Unexpected error occurred. Try again later..",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //method to accept HTTP PATCH request to update an event.
    @PatchMapping
    public ResponseEntity<?> updateEvent(@RequestBody Event event) throws EventNotFoundException,Exception {
        try {
            return new ResponseEntity<>(eventService.updateEvent(event), HttpStatus.OK) ;
        }catch (EventNotFoundException eventNotFoundException){
            log.error("EventNotFoundError occurred in EventController -> updateEvent()");
            return new ResponseEntity<>("Sorry for inconvenience! We will be back soon.",HttpStatus.CONFLICT);
        }catch (Exception e){
            log.error("Exception occurred in EventController -> updateEvent() ");
            return new ResponseEntity<>("Sorry for inconvenience! We will be back soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //method to accept HTTP GET request to retrieve an event(searched by its eventId) and send the object as response
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventByEventId(@PathVariable String eventId) throws EventNotFoundException, Exception {
        try {
            return new ResponseEntity<>(eventService.getEventById(eventId),HttpStatus.OK);
        }catch (EventNotFoundException eventNotFoundException){
            log.error("EventNotFoundError occurred in EventController -> getEventByEventId()");
            return new ResponseEntity<>("Sorry for inconvenience! We will be back soon.",HttpStatus.CONFLICT);
        }catch (Exception e){
            log.error("Exception occurred in EventController -> getEventByEventId()");
            return new ResponseEntity<>("Sorry for inconvenience! We will be back soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //method to accept HTTP GET request to retrieve ALL events and send list of event objects as response
    @GetMapping
    public ResponseEntity<?> getAllEvents() throws Exception{
        try{
            return new ResponseEntity<>(eventService.getAllEvents(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in EventController -> getEventByEventId() ");
            return new ResponseEntity<>("Sorry for inconvenience! We will be back soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.stackroute.controller;

import com.stackroute.exception.EventNotFoundException;
import com.stackroute.model.Event;
import com.stackroute.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EventController {
    //addEvent               = POST =
    //updateEvent            = PATCH = /
    //getEventByEventId      = GET = /{eventId}

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        return new ResponseEntity<>(eventService.addEvent(event), HttpStatus.CREATED) ;
    }
    @PatchMapping
    public ResponseEntity<?> updateEvent(@RequestBody Event event) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.updateEvent(event), HttpStatus.OK) ;
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventByEventId(@PathVariable String eventId) throws Exception {
        return new ResponseEntity<>(eventService.getEventById(eventId),HttpStatus.OK);
    }
}

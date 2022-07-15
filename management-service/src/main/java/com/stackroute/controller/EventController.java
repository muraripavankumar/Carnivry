package com.stackroute.controller;

import com.stackroute.model.Event;
import com.stackroute.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class EventController {
    //addEvent               = POST = /
    //updateEvent            = PATCH = /
    //getEventByEventId      = GET = /{eventId}

    @Autowired
    private EventService eventService;

    @PostMapping("/")
    public ResponseEntity<?> addEvent(@RequestParam("event") String eventText, @RequestParam("image") MultipartFile posterPic) throws IOException {
        return new ResponseEntity<>(eventService.addEvent(eventText,posterPic), HttpStatus.CREATED) ;
    }
    @PatchMapping("/")
    public ResponseEntity<?> updateEvent(@RequestParam("event") String eventText, @RequestParam("image") MultipartFile posterPic) throws IOException {
        return new ResponseEntity<>(eventService.updateEvent(eventText,posterPic), HttpStatus.OK) ;
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventByEventId(@PathVariable String eventId) throws Exception {
        return new ResponseEntity<>(eventService.getEventById(eventId),HttpStatus.OK);
    }
}

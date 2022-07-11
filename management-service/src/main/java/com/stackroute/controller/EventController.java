package com.stackroute.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EventController {
    //addEvent = POST
    //updateEvent = PATCH
    @PostMapping("/")
    public ResponseEntity<?> addEvent(@RequestParam())
}

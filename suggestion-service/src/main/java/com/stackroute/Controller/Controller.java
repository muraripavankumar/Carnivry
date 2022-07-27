package com.example.SuggestionService.Controller;

import com.example.SuggestionService.Services.EventService;
import com.example.SuggestionService.Services.UserService;
import com.example.SuggestionService.entity.Events;
import com.example.SuggestionService.entity.User;
import com.example.SuggestionService.exception.EventAlreadyExistException;
import com.example.SuggestionService.exception.EventNotFoundException;
import com.example.SuggestionService.exception.UserAlreadyExistException;
import com.example.SuggestionService.exception.UserNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Controller {


    UserService userService;
    EventService eventService;

    @Autowired
    public Controller(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    ResponseEntity responseEntity;

    //add new user
    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            responseEntity = new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
        }
        catch (UserAlreadyExistException u){
            responseEntity = new ResponseEntity<String>("User already exist with the same email id", HttpStatus.OK);
        }
        return responseEntity;
    }

    //add new event
    @PostMapping("/add-event")
    public ResponseEntity<?> addEvents(@RequestBody Events events){
        try {
            responseEntity = new ResponseEntity<Events>(eventService.addEvents(events), HttpStatus.OK);
        }
        catch (EventAlreadyExistException e){
            responseEntity= new ResponseEntity<String >("Event already exist with the same Id", HttpStatus.ALREADY_REPORTED);
        }
        return responseEntity;
    }

    //update Event
    @PutMapping("/update-event")
    public ResponseEntity<?> updateEvents(@RequestBody Events events){
        try {
            responseEntity = new ResponseEntity<Events>(eventService.updateEvent(events), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String >("Event not found", HttpStatus.OK);
        }
        return responseEntity;
    }

    //Retrieve upcoming events
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/upcoming-events/{emailId}")
    public ResponseEntity<?> upcomingEvents(@PathVariable String emailId){
        try {
            responseEntity = new ResponseEntity<List<Events>>(userService.getUpcomingEvents(emailId), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String>("No Upcoming events found", HttpStatus.OK);
        }
        return responseEntity;
    }

    //retrieve all users
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        try {
            responseEntity = new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
        }
        catch (UserNotfoundException u){
            responseEntity = new ResponseEntity<String>("No user found", HttpStatus.OK);
        }
        return responseEntity;
    }

    //retrieve all the events
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all-events")
    public ResponseEntity<?> getAllEvents() {
        try {
            responseEntity = new ResponseEntity<List<Events>>(eventService.getAllEvents(), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String>("No Event list found", HttpStatus.OK);
        }
        return responseEntity;
    }

    //update the number of likes of event
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update-likes/{emailId}/{eventId}")
    public ResponseEntity<?> updateEventLikes(@PathVariable String emailId, @PathVariable String eventId){
        try {
            responseEntity = new ResponseEntity<String>(eventService.updateEventLikes(emailId, eventId), HttpStatus.OK);
        }
        catch (Exception e){
            responseEntity = new ResponseEntity<String>("Some other exception occurred", HttpStatus.OK);
        }
        return responseEntity;
    }

    //update the wishlist of the user
    @PutMapping("/add-wishlist/{emailId}/{eventId}")
    public ResponseEntity<?> updateUserWishlist(@PathVariable String emailId, @PathVariable String eventId){
        try {
            responseEntity = new ResponseEntity<String>(userService.updateUserWishlist(emailId, eventId), HttpStatus.OK);
        }
        catch (EventAlreadyExistException e){
            responseEntity = new ResponseEntity<String>("Event already exist in wishlist", HttpStatus.OK);
        }
        return responseEntity;
    }

    //retrieve recommended events for particular user
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/suggest-events/{emailId}")
    public ResponseEntity<?> getSuggestedEvents(@PathVariable String emailId){
        try {
            responseEntity = new ResponseEntity<List<Events>>(userService.getSuggestedEvents(emailId), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String>("No Suggestions found for you", HttpStatus.OK);
        }
        return responseEntity;
    }
}
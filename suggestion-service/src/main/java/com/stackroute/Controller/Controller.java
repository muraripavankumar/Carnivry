package com.stackroute.Controller;

import com.stackroute.Services.EventService;
import com.stackroute.Services.UserService;
import com.stackroute.entity.Events;
import com.stackroute.entity.User;
import com.stackroute.exception.EventAlreadyExistException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserAlreadyExistException;
import com.stackroute.exception.UserNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin("**")
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

//    //add new event
//    @PostMapping("/add-event")
//    public ResponseEntity<?> addEvents(@RequestBody Events events){
//        try {
//            responseEntity = new ResponseEntity<Events>(eventService.addEvents(events), HttpStatus.OK);
//        }
//        catch (EventAlreadyExistException e){
//            responseEntity= new ResponseEntity<String >("Event already exist with the same Id", HttpStatus.ALREADY_REPORTED);
//        }
//        return responseEntity;
//    }

    //update Event
//    @PutMapping("/update-event")
//    public ResponseEntity<?> updateEvents(@RequestBody Events events){
//        try {
//            responseEntity = new ResponseEntity<Events>(eventService.updateEvent(events), HttpStatus.OK);
//        }
//        catch (EventNotFoundException e){
//            responseEntity = new ResponseEntity<String >("Event not found", HttpStatus.OK);
//        }
//        return responseEntity;
//    }

    //Retrieve upcoming events

    @GetMapping("/upcoming-events")
    public ResponseEntity<?> upcomingEvents(){
        try {
            responseEntity = new ResponseEntity<List<Events>>(userService.getUpcomingEvents(), HttpStatus.OK);
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
//    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all-events")
    public ResponseEntity<?> getAllEvents() {
        try {
            responseEntity = new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String>("No Event list found", HttpStatus.OK);
        }
        return responseEntity;
    }

    //update the number of likes of event
//    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update-likes/{emailId}/{eventId}")
    public ResponseEntity<?> updateEventLikes(@PathVariable String emailId, @PathVariable String eventId){
        try {
            responseEntity = new ResponseEntity<Events>(eventService.updateEventLikes(emailId, eventId), HttpStatus.OK);
        }
        catch (Exception e){
            responseEntity = new ResponseEntity<String>("Some other exception occurred", HttpStatus.OK);
        }
        return responseEntity;
    }

    //update the wishlist of the user
//    @CrossOrigin(origins = "http://localhost:4200")
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
//    @CrossOrigin(origins = "http://localhost:4200")
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

    //retrieve recommended events for particular log out session
//    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/suggest-events/no-user")
    public ResponseEntity<?> getSuggestedEventsForLogout(){
        try {
            responseEntity = new ResponseEntity<List<Events>>(userService.getSuggestedEventsForLogout(), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String>("No Suggestions found for you", HttpStatus.OK);
        }
        return responseEntity;
    }

    //retrieve recommended events for particular city
//    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/suggestion/{city}")
    public ResponseEntity<?> getSuggestedEventsByCity(@PathVariable String city){
        try {
            responseEntity = new ResponseEntity<List<Events>>(userService.getSuggestedEventsByCity(city), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            responseEntity = new ResponseEntity<String>("No Suggestions found for you", HttpStatus.OK);
        }
        return responseEntity;
    }
}
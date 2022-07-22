package com.example.SuggestionService.Controller;

import com.example.SuggestionService.Services.EventService;
import com.example.SuggestionService.Services.UserService;
import com.example.SuggestionService.entity.Events;
import com.example.SuggestionService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Controller {


    private final UserService userService;

    private final EventService eventService;
    ResponseEntity responseEntity;

    @Autowired
    public Controller(UserService userService, EventService eventService, ResponseEntity responseEntity) {
        this.userService = userService;
        this.eventService = eventService;
        this.responseEntity=responseEntity;
    }

    //add new user
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user){
        responseEntity= new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
        return responseEntity;
    }

    //add new event
    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvents(@RequestBody Events events){
        responseEntity= new ResponseEntity<Events>(eventService.addEvents(events), HttpStatus.OK);
        return responseEntity;
    }

    //update Event
    @PutMapping("/updateEvent")
    public ResponseEntity<?> updateEvents(@RequestBody Events events){
        responseEntity= new ResponseEntity<Events>(eventService.updateEvent(events), HttpStatus.OK);
        return responseEntity;
    }

    //Retrieve upcoming events
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/upcomingEvents/{emailId}")
    public ResponseEntity<?> upcomingEvents(@PathVariable String emailId){
        responseEntity= new ResponseEntity<List<Events>>(userService.getUpcomingEvents(emailId), HttpStatus.OK);
        return responseEntity;
    }

    //retrieve all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        responseEntity= new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
        return responseEntity;
    }

    //retrieve all the events
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getEventsOfUserCity() {
        responseEntity = new ResponseEntity<List<Events>>(eventService.getAllEvents(), HttpStatus.OK);
        return responseEntity;
    }

    //update the number of likes of event
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/likeEvent/{emailId}/{eventId}")
    public ResponseEntity<?> updateEventLikes(@PathVariable String emailId, @PathVariable String eventId){
        responseEntity= new ResponseEntity<String>(eventService.updateEventLikes(emailId,eventId), HttpStatus.OK);
        return responseEntity;
    }

    //update the wishlist of the user
    @PutMapping("/addWishlist/{emailId}/{eventId}")
    public ResponseEntity<?> updateUserWishlist(@PathVariable String emailId, @PathVariable String eventId){
        responseEntity= new ResponseEntity<String>(userService.updateUserWishlist(emailId,eventId), HttpStatus.OK);
        return responseEntity;
    }

    //retrieve recommended events for particular user
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getSuggestedEvents/{emailId}")
    public ResponseEntity<?> getSuggestedEvents(@PathVariable String emailId){
        responseEntity= new ResponseEntity<List<Events>>(userService.getSuggestedEvents(emailId), HttpStatus.OK);
        return responseEntity;
    }
}

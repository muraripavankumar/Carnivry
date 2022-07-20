package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    private String eventId;
    private String title;
    private String userEmailId;
    private String eventDescription;
    private List<String> artists;
    private List<String> genre;
    private List<String> languages;
    private EventTiming eventTimings;
    private String poster;
    private Venue venue;
    private double revenueGenerated;
    private int ticketsSold;
    private int totalSeats;
    private List<Seat> seats;
    private int likes;
    private List<String> emailOfUsersLikedEvent;







//    //constructor to be used for creating new event
//    public Event(String title, String userEmailId, String eventDescription, List<String> artists, List<String> genre, List<String> languages, EventTiming eventTimings, String poster, Venue venue, int totalSeats, List<Seat> seats) {
//        this.title = title;
//        this.userEmailId = userEmailId;
//        this.eventDescription = eventDescription;
//        this.artists = artists;
//        this.genre = genre;
//        this.languages = languages;
//        this.eventTimings = eventTimings;
//        this.poster = poster;
//        this.venue = venue;
//        this.totalSeats = totalSeats;
//        this.seats = seats;
//        revenueGenerated=0;
//        ticketsSold=0;
//        likes=0;
//        emailOfUsersLikedEvent=new ArrayList<String>();
//    }
//    //constructor to be used for updating the event details
//    public Event(String eventId, String title, String userEmailId, String eventDescription, List<String> artists, List<String> genre, List<String> languages, EventTiming eventTimings, String poster, Venue venue, int totalSeats, List<Seat> seats) {
//        this.eventId = eventId;
//        this.title = title;
//        this.userEmailId = userEmailId;
//        this.eventDescription = eventDescription;
//        this.artists = artists;
//        this.genre = genre;
//        this.languages = languages;
//        this.eventTimings = eventTimings;
//        this.poster = poster;
//        this.venue = venue;
//        this.totalSeats = totalSeats;
//        this.seats = seats;
//    }
}

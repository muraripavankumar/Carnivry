package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private Binary poster;
    private String fileName;
    private String fileType;
    private Venue venue;
    private double revenueGenerated;
    private int ticketsSold;
    private int totalSeats;
    private List<Seat> seats;
    private int likes;

    public Event(String eventId, String title, String userEmailId, String eventDescription, List<String> artists, List<String> genre, List<String> languages, double revenueGenerated, int ticketsSold, int totalSeats, int likes) {
        this.eventId = eventId;
        this.title = title;
        this.userEmailId = userEmailId;
        this.eventDescription = eventDescription;
        this.artists = artists;
        this.genre = genre;
        this.languages = languages;
        this.revenueGenerated = revenueGenerated;
        this.ticketsSold = ticketsSold;
        this.totalSeats = totalSeats;
        this.likes = likes;
    }
}

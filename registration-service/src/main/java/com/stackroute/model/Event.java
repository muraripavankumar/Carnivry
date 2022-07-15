package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

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


}

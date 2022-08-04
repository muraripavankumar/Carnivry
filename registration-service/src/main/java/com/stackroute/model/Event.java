package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private List<String> posters;
    private Venue venue;
    private BigDecimal revenueGenerated;
    private int ticketsSold;
    private int totalSeats;
    private List<Seat> seats;
    private int likes;
    private List<String> emailOfUsersLikedEvent;

}

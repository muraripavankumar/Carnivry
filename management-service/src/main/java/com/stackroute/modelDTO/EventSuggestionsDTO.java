package com.stackroute.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class EventSuggestionsDTO {
    private String eventId;
    private String title;
    private String eventType;
    private String userEmailId;
    private String eventDescription;
    private List<String> artists;
    private List<String> genre;
    private List<String> languages;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
    private String poster;
    private String venue;
    private String houseNo;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private int pincode;
    private int ticketsSold;
    private Double revenueGenerated;
    private Double price;
    private int totalSeats;
    private int likes;
    private List<String> emailOfUsersLikedEvent;
}

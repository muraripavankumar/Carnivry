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
    private List<String> genre;
    private List<String> languages;
    private Date startDate;
    private Date endDate;
    private String poster;
    private String city;
    private int ticketsSold;
    private Double price;
    private int totalSeats;
}

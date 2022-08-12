package com.stackroute.model;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventDTO {
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
    private int ticketsSold;   //discuss with sir
    private Double price;
    private int totalSeats;
}

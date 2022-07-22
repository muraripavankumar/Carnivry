package com.example.SuggestionService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;
import org.bson.types.Binary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NodeEntity
public class Events {
    @GraphId
    @Id
    private String eventId;
    //@NotNull
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
    private BigDecimal revenueGenerated;
    private int ticketsSold;
    private int totalSeats;
//    private List<Seat> seats;
    private int likes;
    private List<String> emailOfUsersLikedEvent;
}
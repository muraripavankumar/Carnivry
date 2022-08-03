package com.stackroute.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Document
@Data
@AllArgsConstructor
public class Event implements Serializable {

    private static final long serialVersionUID = -4439114469417994311L;
    @Id
    private String eventId;
    @NotNull
    private String title;
    @NotNull
    private String userEmailId;
    private String eventDescription;
    private List<String> artists;
    private List<String> genre;
    private List<String> languages;
    @NotNull
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

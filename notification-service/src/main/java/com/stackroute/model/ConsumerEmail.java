package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ConsumerEmail {
    String eventConsumerEmailId;
    String eventTitle;
    String eventConsumerName;
    EventTiming eventTimings;
    Venue venue;
    Double ticketPrice;
    List<Seat> boughtSeats;
}

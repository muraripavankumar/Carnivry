package com.stackroute.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsumerEmail {
    String eventConsumerEmailId;
    String eventTitle;
    String eventDescription;
    String eventConsumerName;
    EventTiming eventTimings;
    Venue venue;
    BigDecimal ticketPrice;
    List<Seat> boughtSeats;
    int noOfSeats;
}

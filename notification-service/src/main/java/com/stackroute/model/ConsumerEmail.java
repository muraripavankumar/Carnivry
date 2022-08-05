package com.stackroute.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsumerEmail {
    String eventConsumerEmailId;
    String eventTitle;
    String eventConsumerName;
    EventTiming eventTimings;
    Venue venue;
    Double ticketPrice;
    List<Seat> boughtSeats;
}

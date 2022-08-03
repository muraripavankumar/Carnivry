package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProducerEmail {
    String eventProducerEmailId;
    String eventTitle;
    String eventProducerName;
    EventTiming eventTimings;
    Venue venue;
    int totalSeats;

}

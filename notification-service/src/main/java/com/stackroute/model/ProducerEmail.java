package com.stackroute.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProducerEmail {
    String eventProducerEmailId;
    String eventTitle;
    String eventProducerName;
    EventTiming eventTimings;
    Venue venue;
    int totalSeats;

}

package com.stackroute.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerEmail {
    @NotNull
    String eventProducerEmailId;
    @NotNull
    String eventTitle;
    @NotNull
    String eventProducerName;
    @NotNull
    EventTiming eventTimings;
    Venue venue;
    int totalSeats;


}

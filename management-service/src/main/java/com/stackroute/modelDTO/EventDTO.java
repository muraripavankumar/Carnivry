package com.stackroute.modelDTO;

import com.stackroute.model.EventTiming;
import com.stackroute.model.Venue;
import lombok.*;

@Data
@AllArgsConstructor
public class EventDTO {
    String eventProducerEmailId;
    String eventTitle;
    String eventProducerName;
    EventTiming eventTimings;
    Venue venue;
    int totalSeats;
}

package com.stackroute.rabbitmq;

import com.stackroute.model.EventTiming;
import com.stackroute.model.Venue;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {
    String eventProducerEmailId;
    String eventTitle;
    String eventProducerName;
    EventTiming eventTimings;
    Venue venue;
    int totalSeats;

}

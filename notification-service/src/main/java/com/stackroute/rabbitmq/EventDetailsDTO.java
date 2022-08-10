package com.stackroute.rabbitmq;

import com.stackroute.model.EventTiming;
import com.stackroute.model.Seat;
import com.stackroute.model.Venue;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDetailsDTO {
    String eventConsumerEmailId;
    String eventTitle;
    String eventDescription;
    String eventConsumerName;
    EventTiming eventTimings;
    Venue venue;
    BigDecimal ticketPrice;
    List<Seat> boughtSeats;
    private int NoOfSeats;
}

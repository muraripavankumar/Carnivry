package com.stackroute.rabbitMq;

import com.stackroute.model.EventTiming;
import com.stackroute.model.Seat;
import com.stackroute.model.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationServiceDTO {
    private String eventId, title, description, email, username, amount;
    private Venue venue;
    private EventTiming timings;
    private List<Seat> seats;
    private int NoOfSeats;
}

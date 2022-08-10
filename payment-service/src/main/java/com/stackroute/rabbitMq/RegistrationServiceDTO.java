package com.stackroute.rabbitMq;

import com.stackroute.model.EventTiming;
import com.stackroute.model.Seat;
import com.stackroute.model.Venue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationServiceDTO {
    private String eventId, title, description, email, image, host;
    private List<String> artists;
    private Venue venue;
    private EventTiming timings;
    private List<Seat> seats;
    private int noOfSeats;
}

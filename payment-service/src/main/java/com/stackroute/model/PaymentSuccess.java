package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccess {
    private String orderId, paymentId, signature, eventId, title, description, email, username ,host;
    private List<String> artists;
    private String image;
    private Venue venue;
    private EventTiming timings;
    private List<Seat> seats;
    private int noOfSeats;
    private BigDecimal amount;
}

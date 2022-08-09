package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccess {
    private String orderId, paymentId, signature, eventId, title, description, username, email;
    private String image;
    private BigDecimal amount;
    private String host;
    private Venue venue;
    private EventTiming timings;
    private Seat seats;
}

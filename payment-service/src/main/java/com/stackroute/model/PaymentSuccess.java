package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccess {
    String orderId, paymentId, signature, eventId, amount, title, description, username, email;
    private Venue venue;
    private EventTiming timings;
    private Seat seats;
}

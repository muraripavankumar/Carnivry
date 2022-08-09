package com.stackroute.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatDetails {
    private int seatId;
    private String seatCategory;
    private BigDecimal seatPrice;

}

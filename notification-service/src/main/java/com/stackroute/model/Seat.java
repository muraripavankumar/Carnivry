package com.stackroute.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seat {
    private int row;
    private int colm;
    private int seatId;
    private BigDecimal seatPrice;
    private String status;
}

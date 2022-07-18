package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private int row;
    private int colm;
    private int seatId;
    private double seatPrice;

    public Seat(int row, int colm, double seatPrice) {
        this.row = row;
        this.colm = colm;
        this.seatPrice = seatPrice;
    }
}

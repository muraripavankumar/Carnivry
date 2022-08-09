package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {


    private int row;
    private int colm;
    private int seatId;
    private BigDecimal seatPrice;
    private String status;
    private String seatCategory;

}

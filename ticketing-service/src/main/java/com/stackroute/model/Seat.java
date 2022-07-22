package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat implements Serializable {

    private static final long serialVersionUID = -4439114469417994311L;


    private int row;
    private int colm;
    private int seatId;
    private BigDecimal seatPrice;
    private String status;

    public Seat(int row, int colm, BigDecimal seatPrice) {
        this.row = row;
        this.colm = colm;
        this.seatPrice = seatPrice;
    }
}

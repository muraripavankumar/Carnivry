package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Seat implements Serializable {

    private static final long serialVersionUID = -4439114469417994311L;


    private int row;
    private int colm;
    private int seatId;
    private BigDecimal seatPrice;
    private String status;
    private String seatCategory;
}

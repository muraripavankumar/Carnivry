package com.stackroute.model;


import lombok.Data;


import java.io.Serializable;

@Data

public class Venue implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;

    private String venueName;
    private Address address;
}

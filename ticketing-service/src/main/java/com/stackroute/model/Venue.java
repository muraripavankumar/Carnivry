package com.stackroute.model;


import lombok.AllArgsConstructor;
import lombok.Data;



import java.io.Serializable;

@Data
@AllArgsConstructor
public class Venue implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;

    private String venueName;
    private Address address;
}

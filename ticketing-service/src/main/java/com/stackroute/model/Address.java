package com.stackroute.model;

import lombok.Data;
import java.io.Serializable;

@Data

public class Address implements Serializable {

    private static final long serialVersionUID = -4439114469417994311L;

    private String house;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private int pincode;
}

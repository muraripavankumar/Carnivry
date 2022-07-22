package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

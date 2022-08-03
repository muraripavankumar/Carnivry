package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data

public class Address {
    private String house;
    private String street;

    private String landmark;

    private String city;

    private String state;

    private String country;
    private int pincode;
}

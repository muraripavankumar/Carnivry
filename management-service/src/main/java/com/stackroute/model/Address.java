package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String house;
    private String street;
    @NotNull
    private String landmark;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String country;
    private int pincode;
}

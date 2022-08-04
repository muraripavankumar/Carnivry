package com.stackroute.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    private String house;
    private String street;

    private String landmark;

    private String city;

    private String state;

    private String country;
    private int pincode;
}

package com.stackroute.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAddress {
    String email;
    String house;
    String street;
    String landmark;
    String city;
    String state;
    String country;
    String pincode;
}

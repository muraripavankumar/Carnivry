package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;



import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = -4439114469417994311L;

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
    @NotNull
    private int pincode;

}

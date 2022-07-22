package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venue implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;

    private String venueName;
    private Address address;
}

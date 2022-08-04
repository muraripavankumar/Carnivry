package com.stackroute.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Venue {

    private String venueName;
    private Address address;
}

package com.stackroute.entity;

import com.stackroute.model.Address;
import com.stackroute.model.Event;
import com.stackroute.model.Preferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarnivryUser {
    @Id
    private String id;

    private String name, email, phone;

    private Date dob;

    private Address address;

    private Preferences preferences;

    private List<Event> attendedEvents;
    private List<Event> postedEvents;

    private List<Event> wishlist;

    private Binary profilePic;

    private Boolean verified;

    private String emailVerificationToken;
    private Date evtExpTime;

    private String passwordVerificationToken;
    private Date pvtExpTime;


}

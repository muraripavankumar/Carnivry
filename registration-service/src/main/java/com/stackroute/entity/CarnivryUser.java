package com.stackroute.entity;

import com.stackroute.model.Address;
import com.stackroute.model.Event;
import com.stackroute.model.Preferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarnivryUser {
    @Id
    long id;

    @Transient
    public static final String SEQUENCE_NAME= "Carnivry_User_sequence";

    String email, password, phone;

    Date dob;

    Address address;

    Preferences preferences;

    List<Event> attendedEvents;
    List<Event> postedEvents;

    List<Event> wishlist;



    Binary profilePic;


}

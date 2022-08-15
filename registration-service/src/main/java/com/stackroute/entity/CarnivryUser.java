package com.stackroute.entity;

import com.stackroute.model.Address;
import com.stackroute.model.Event;
import com.stackroute.model.Preferences;
import com.stackroute.rabbitMQ.TicketDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarnivryUser {
    @Id
    @Email
    private String email;

     private  String emailId;

    @NotEmpty
    private String name;
    private String phone;

    private Date dob;

    private Address address;

    private Preferences preferences;

   private Set<TicketDTO> tickets;

    private List<Event> postedEvents;

    private List<Event> wishlist;

    private String profilePic;

    @NotEmpty
    private Boolean verified;

    private String emailVerificationToken;
    private Date evtExpTime;

    private String passwordVerificationToken;
    private Date pvtExpTime;

    private String phoneNoVerificationOTP;
    private Date pvoExpTime;
}

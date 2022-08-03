package com.stackroute;


import com.stackroute.Repository.EventRepository;
import com.stackroute.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TicketingServiceRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private Address address;
    private Event event;
    private EventTiming eventTiming;
    private Venue venue;


    @BeforeEach
    public void setup(){
        address = new Address("221B","Baker Street","Sherlock's House","Los Angeles","California","United States",5656568);
        eventTiming=new EventTiming(new Date(),new Date(),"17:00","19:00");
        Seat seat1 = new Seat(10,2,1,new BigDecimal(25.00),"Not Booked","Platinum");
        Seat seat2 = new Seat(10,2,2,new BigDecimal(25.00),"Processing","Platinum");
        Seat seat3 = new Seat(10,2,3,new BigDecimal(25.00),"Booked","Platinum");
        //  ArrayList<Seat> seatList=new ArrayList<>(Arrays.asList(seat1,seat2,seat3));
        ArrayList<Seat> seatList=new ArrayList<>();
        seatList.add(seat1);
        seatList.add(seat2);
        seatList.add(seat3);
        venue = new Venue("Hollywood Boulevard",address);
        ArrayList<String> artistList= new ArrayList<String>(Arrays.asList("artist","artists"));
        ArrayList<String> genreList=new ArrayList<String>(Arrays.asList("genre1","genre2"));
        ArrayList<String> languageList=new ArrayList<String>(Arrays.asList("language1","language2"));
        ArrayList<String> emailList=new ArrayList<>(Arrays.asList("me@gmail.com","you@gmail.com","everyone@gmail.com"));
        ArrayList<String> posters = new ArrayList<>(Arrays.asList("url","url"));
        event = new Event("1","Movie","thismy@email.com","Movie about movie",
                artistList,genreList,languageList,eventTiming,posters,venue,new BigDecimal(0.00),0,
                200,seatList,200,emailList);
    }

    @AfterEach
    public void close(){
        event=null;
        eventRepository.deleteAll();;
    }

    @Test
    public void getEvent(){
        eventRepository.insert(event);
        Event result = eventRepository.findById(event.getEventId()).get();
        assertEquals(result.getEventId(),event.getEventId());
    }

    @Test
    public void getTicketStatus(){
        eventRepository.insert(event);
        Event result = eventRepository.findById(event.getEventId()).get();
        assertEquals(result.getSeats().get(0).getStatus(),event.getSeats().get(0).getStatus());
    }


}

package com.stackroute;

import com.stackroute.model.*;
import com.stackroute.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ManagementServieceEventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;
    private Event event;

    @BeforeEach
    public void init(){
        ArrayList<String> artistList= new ArrayList<String>(Arrays.asList("artist","artists"));
        ArrayList<String> genreList=new ArrayList<String>(Arrays.asList("genre1","genre2"));
        ArrayList<String> languageList=new ArrayList<String>(Arrays.asList("language1","language2"));
        ArrayList<String> posters=new ArrayList<String>(Arrays.asList("poster_thumbnail","poster_landscape"));
        ArrayList<Seat> seatArrayList=new ArrayList<>();
        ArrayList<String> emailList=new ArrayList<>(Arrays.asList("user1@ex.com","user2@ex.com","user3@ex.com"));
        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
        Venue venue=new Venue("Test venue name",address);
        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
        Seat seat1=new Seat(5,6,1,new BigDecimal("100.05"),"NOT_BOOKED","platinum");
        Seat seat2=new Seat(5,6,2,new BigDecimal("200.05"),"NOT_BOOKED","silver");
        Seat seat3=new Seat(5,6,3,new BigDecimal("10.05"),"NOT_BOOKED","gold");
        seatArrayList.add(seat1);
        seatArrayList.add(seat2);
        seatArrayList.add(seat3);

        event=new Event("101","Test example tile","testuser@example.com","name1","test event description",artistList,genreList,languageList,eventTiming,posters, venue,new BigDecimal(2000),250,450,seatArrayList,300,emailList);

    }
    @AfterEach
    public void conclude(){
        event=null;
        eventRepository.deleteAll();
    }
    @Test
    public void addEvent(){
        eventRepository.deleteAll();
        eventRepository.insert(event);
        List<Event> events=eventRepository.findAll();
        assertEquals(1,events.size());
    }
    @Test
    public void findAllEventsByUserEmailId(){
        ArrayList<String> artistList= new ArrayList<String>(Arrays.asList("artist","artists"));
        ArrayList<String> genreList=new ArrayList<String>(Arrays.asList("genre1","genre2"));
        ArrayList<String> languageList=new ArrayList<String>(Arrays.asList("language1","language2"));
        ArrayList<String> posters=new ArrayList<String>(Arrays.asList("poster_thumbnail","poster_landscape"));
        ArrayList<Seat> seatArrayList=new ArrayList<>();
        ArrayList<String> emailList=new ArrayList<>(Arrays.asList("user1@ex.com","user2@ex.com","user3@ex.com"));
        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
        Venue venue=new Venue("Test venue name",address);
        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
        Seat seat1=new Seat(5,6,1,new BigDecimal("100.05"),"NOT_BOOKED","platinum");
        Seat seat2=new Seat(5,6,2,new BigDecimal("200.05"),"NOT_BOOKED","silver");
        Seat seat3=new Seat(5,6,3,new BigDecimal("10.05"),"NOT_BOOKED","gold");
        seatArrayList.add(seat1);
        seatArrayList.add(seat2);
        seatArrayList.add(seat3);

        Event event1=new Event("102","Test example tile","testuser1@example.com","name1","test event description",artistList,genreList,languageList,eventTiming,posters, venue,new BigDecimal(2000),250,450,seatArrayList,300,emailList);

        eventRepository.insert(event);
        eventRepository.insert(event1);

        List<Event> result=eventRepository.findByUserEmailId("testuser@example.com");
        assertEquals(1,result.size());
        assertEquals("testuser@example.com",result.get(0).getUserEmailId());
    }
}

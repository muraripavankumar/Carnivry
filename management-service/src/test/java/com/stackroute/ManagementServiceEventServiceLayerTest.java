package com.stackroute;

import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;
import com.stackroute.modelDTO.EventDTO;
import com.stackroute.rabbitmq.Producer;
import com.stackroute.repository.EventRepository;
import com.stackroute.service.EventServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagementServiceEventServiceLayerTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private Producer producer;
    @InjectMocks
    private EventServiceImpl eventService;
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
        producer=null;
        eventRepository.deleteAll();
    }
    @Test
    public void addEventReturnTrueTest() throws Exception {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        when(eventRepository.insert(any(Event.class))).thenReturn(event);
        EventDTO eventDTO=new EventDTO(event.getUserEmailId(),event.getTitle(),event.getUserName(),event.getEventTimings(),event.getVenue(),event.getTotalSeats());
        assertTrue(eventService.addEvent(event));
        verify(eventRepository,times(1)).findById(event.getEventId());
    }
    @Test
    public void addEventThrowErrorTest(){
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertThrows(EventAlreadyExistsException.class,()->eventService.addEvent(event));
    }
    @Test
    public void updateEventReturnTrueTest() throws Exception {
        ArrayList<String> artistList= new ArrayList<String>(Arrays.asList("artist","artists"));
        ArrayList<String> genreList=new ArrayList<String>(Arrays.asList("genre1","genre2"));
        ArrayList<String> languageList=new ArrayList<String>(Arrays.asList("language1","language2"));
        ArrayList<String> posters=new ArrayList<String>(Arrays.asList("poster_thumbnail","poster_landscape"));
        ArrayList<Seat> seatArrayList=new ArrayList<>();
        ArrayList<String> emailList=new ArrayList<>(Arrays.asList("user1@ex.com","user2@ex.com","user3@ex.com","user4@ex.com"));
        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
        Venue venue=new Venue("Test venue name",address);
        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
        Seat seat1=new Seat(5,6,1,new BigDecimal("100.05"),"NOT_BOOKED","platinum");
        Seat seat2=new Seat(5,6,2,new BigDecimal("200.05"),"NOT_BOOKED","silver");
        Seat seat3=new Seat(5,6,3,new BigDecimal("10.05"),"NOT_BOOKED","gold");
        seatArrayList.add(seat1);
        seatArrayList.add(seat2);
        seatArrayList.add(seat3);

        Event event1=new Event("101","Test example tile","testuser@example.com","name1","test event description",artistList,genreList,languageList,eventTiming,posters, venue,new BigDecimal(2000),350,450,seatArrayList,300,emailList);


        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        when(eventRepository.save(event1)).thenReturn(event1);
        assertTrue(eventService.updateEvent(event1));
    }
    @Test
    public void updateEventThrowErrorTest(){
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->eventService.updateEvent(event));
    }
    @Test
    public void getEventByIdReturnEventTest() throws Exception {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertEquals(event,eventService.getEventById("101"));
    }
    @Test
    public void getEventByIdThrowErrorTest(){
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->eventService.getEventById("101"));
    }
    @Test
    public void getAllEventsTest() throws Exception {
        when(eventRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(event)));
        assertEquals(1,eventService.getAllEvents().size());
    }
    @Test
    public void getAllEventsByUserEmailIdTest() throws Exception {
        when(eventRepository.findByUserEmailId(any(String.class))).thenReturn(new ArrayList<>(Arrays.asList(event)));
        assertEquals(1,eventService.getAllEventsByUserEmailId("useremail@gmail.com").size());
    }
    @Test
    public void getAllEventsByUserEmailIdThrowUserNotFoundExceptionTest(){
        when(eventRepository.findByUserEmailId(any(String.class))).thenReturn(new ArrayList<Event>());
        assertThrows(UserNotFoundException.class,()->eventService.getAllEventsByUserEmailId("email@gmail.com"));
    }
}

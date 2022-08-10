package com.stackroute;

import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.Repository.EventRepository;
import com.stackroute.SchedulerService.PlaygroundService;
import com.stackroute.SchedulerService.SchedulerService;
import com.stackroute.Service.TSImpl;
import com.stackroute.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketingServiceTests {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TSImpl ticketingService;

    @Mock
    private  PlaygroundService playgroundService;

    @Mock
    private SchedulerService schedulerService;

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
        eventRepository.deleteAll();
    }

    @Test
    public void getEventByIdReturnEventTest() throws Exception {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertEquals(event,ticketingService.getEventById("1"));
    }

    @Test
    public void getEventByIdThrowErrorTest(){
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->ticketingService.getEventById("1"));
    }

    @Test
    public void getSeatreturnSeatTest1() throws EventNotFoundException {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertEquals("Processing", ticketingService.getSeat("1", 0).getStatus());
    }

    @Test
    public void getSeatreturnSeatTest2() throws EventNotFoundException {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertEquals("Processing", ticketingService.getSeat("1", 1).getStatus());
    }
    @Test
    public void getSeatThrowErrorTest(){
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->ticketingService.getSeat("1",1));
    }

//    @Test
//    public void getBookedTicketTest() throws EventNotFoundException {
//        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
//        assertEquals("Booked",ticketingService.bookedTicket("1",1).getStatus());
//    }

    @Test
    public void getBookedTicketthrowErrorTest() {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->ticketingService.bookedTicket("1",1));
    }

    @Test
    public void getTicketStatusTest() throws EventNotFoundException {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertEquals(event.getSeats().get(1).getStatus(),ticketingService.ticketStatus("1",1).getStatus());
    }

    @Test
    public void getTicketStatusthrowError() {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->ticketingService.ticketStatus("1",1));
    }

    @Test
    public void getCancelledTicketStatusTest() throws EventNotFoundException {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
        assertEquals("Not Booked",ticketingService.cancelTicket("1",2).getStatus());
    }

    @Test
    public void getCancelledTicketthrowErrorTest() {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->ticketingService.cancelTicket("1",2));
    }

    @Test
    public void getProcessTicketStatus() throws EventNotFoundException {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
            assertEquals("Not Booked", ticketingService.processTicket("1", 1).getStatus());

    }

    @Test
    public void getProcessTicketStatus1() throws EventNotFoundException {
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(event));
            assertEquals("Booked", ticketingService.processTicket("1", 2).getStatus());

    }

    @Test
    public void processTicketErrorTest(){
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
        assertThrows(EventNotFoundException.class,()->ticketingService.processTicket("1",1));
    }
}

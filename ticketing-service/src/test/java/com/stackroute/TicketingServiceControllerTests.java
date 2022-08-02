package com.stackroute;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.Controller.TicketController;
import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.Service.TicketingService;
import com.stackroute.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
public class TicketingServiceControllerTests {

    @Mock
    private TicketingService ticketingService;

    @InjectMocks
    private TicketController ticketController;

    @Autowired
    private MockMvc mockMvc;

    private Address address;
    private Event event;
    private EventTiming eventTiming;
    private Venue venue;
    private Seat seat;

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
        mockMvc= MockMvcBuilders.standaloneSetup(ticketController).build();
        seat = new Seat(10,2,1,new BigDecimal(25.00),"Not Booked","Platinum");
    }

    @AfterEach
    public void close(){
        event=null;
    }

    private String convertToJson(final Object obj){
        String result="";
        try{
            ObjectMapper mapper=new ObjectMapper();
            result=mapper.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void getEventbyIdReturnResponseTest() throws Exception {
        when(ticketingService.getEventById(any(String.class))).thenReturn(event);
        mockMvc.perform(
                get("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event)))
                        .andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }

    //showing not cleared in Jacococd ..
    @Test
    public void getEventByEventIdThrowEventNotFoundExceptionTest() throws Exception {
        when(ticketingService.getEventById(any(String.class))).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                get("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getEventByEventIdThrowExceptionTest() throws Exception {
        when(ticketingService.getEventById(any(String.class))).thenThrow(Exception.class);
        mockMvc.perform(
                get("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getTicketReturnResponse() throws Exception{
        when(ticketingService.getSeat("1",1)).thenReturn(event.getSeats().get(1));
        mockMvc.perform(
                        get("/ticket/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(event.getSeats().get(1))))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getTicketReturnErrorTest() throws Exception{
        when(ticketingService.getSeat("1",5)).thenThrow(IndexOutOfBoundsException.class);
        mockMvc.perform(
                get("/ticket/1/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getTicketReturnEventErrorTest() throws Exception{
        when(ticketingService.getSeat("3",1)).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                get("/ticket/3/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }


    @Test
    public void getBookedTicketReturnResponse() throws Exception{
        when(ticketingService.bookedTicket("1",1)).thenReturn(event.getSeats().get(1));
        mockMvc.perform(
                        get("/ticket/book/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(event.getSeats().get(1))))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getBookedTicketReturnErrorTest() throws Exception{
        when(ticketingService.bookedTicket("1",5)).thenThrow(IndexOutOfBoundsException.class);
        mockMvc.perform(
                get("/ticket/book/1/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getBookedTicketReturnEventErrorTest() throws Exception{
        when(ticketingService.bookedTicket("3",1)).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                get("/ticket/book/3/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getTicketStatusReturnResponse() throws Exception{
        when(ticketingService.ticketStatus("1",1)).thenReturn(event.getSeats().get(1));
        mockMvc.perform(
                        get("/ticket/status/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(event.getSeats().get(1))))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getTicketStatusReturnErrorTest() throws Exception{
        when(ticketingService.ticketStatus("1",5)).thenThrow(IndexOutOfBoundsException.class);
        mockMvc.perform(
                get("/ticket/status/1/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getTicketStatusReturnEventErrorTest() throws Exception{
        when(ticketingService.ticketStatus("3",1)).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                get("/ticket/status/3/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getCancelledTicketReturnResponse() throws Exception{
        when(ticketingService.cancelTicket("1",1)).thenReturn(event.getSeats().get(1));
        mockMvc.perform(
                        get("/ticket/cancel/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(event.getSeats().get(1))))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getCancelTicketReturnErrorTest() throws Exception{
        when(ticketingService.cancelTicket("1",5)).thenThrow(IndexOutOfBoundsException.class);
        mockMvc.perform(
                get("/ticket/cancel/1/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void getCancelTicketReturnEventTest() throws Exception{
        when(ticketingService.cancelTicket("3",1)).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                get("/ticket/cancel/3/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event.getSeats().get(1)))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }
}

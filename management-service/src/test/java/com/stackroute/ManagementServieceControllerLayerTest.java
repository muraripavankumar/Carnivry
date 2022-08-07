package com.stackroute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.controller.EventController;
import com.stackroute.exception.EventAlreadyExistsException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;
import com.stackroute.service.EventService;
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
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ManagementServieceControllerLayerTest {
    @Mock
    private EventService eventService;
    @InjectMocks
    private EventController eventController;
    @Autowired
    private MockMvc mockMvc;
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


        mockMvc= MockMvcBuilders.standaloneSetup(eventController).build();
    }
    @AfterEach
    public void conclude(){
        event=null;
    }
    @Test
    public void addEventReturnResponseTest() throws Exception {
        when(eventService.addEvent(event)).thenReturn(true);
        mockMvc.perform(
                post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void addEventThrowEventAlreadyExistsExceptionTest() throws Exception {
        when(eventService.addEvent(event)).thenThrow(EventAlreadyExistsException.class);
        mockMvc.perform(
                post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void addEventThrowExceptionTest() throws Exception {
        when(eventService.addEvent(event)).thenThrow(Exception.class);
        mockMvc.perform(
                post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isInternalServerError()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void updateEventReturnResponseTest() throws Exception {
        when(eventService.updateEvent(event)).thenReturn(true);
        mockMvc.perform(
                patch("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void updateEvenThrowEventNotFoundExceptionTest() throws Exception {
        when(eventService.updateEvent(event)).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                patch("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void updateEvenThrowExceptionTest() throws Exception {
        when(eventService.updateEvent(event)).thenThrow(Exception.class);
        mockMvc.perform(
                patch("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isInternalServerError()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void getEventByEventIdReturnResponseTest() throws Exception {
        when(eventService.getEventById(any(String.class))).thenReturn(event);
        mockMvc.perform(
                get("/api/v1/event/10122")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void getEventByEventIdThrowEventNotFoundExceptionTest() throws Exception {
        when(eventService.getEventById(any(String.class))).thenThrow(EventNotFoundException.class);
        mockMvc.perform(
                get("/api/v1/event/10122")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void getEventByEventIdThrowExceptionTest() throws Exception {
        when(eventService.getEventById(any(String.class))).thenThrow(Exception.class);
        mockMvc.perform(
                get("/api/v1/event/10122")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(event))
        ).andExpect(status().isInternalServerError()).andDo(MockMvcResultHandlers.log());
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
    public void getAllEventsReturnResponseTest() throws Exception {
        when(eventService.getAllEvents()).thenReturn(new ArrayList<>(List.of(event)));
        mockMvc.perform(
                get("/api/v1")
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void getAllEventsThrowExceptionTest() throws Exception {
        when(eventService.getAllEvents()).thenThrow(Exception.class);
        mockMvc.perform(
                get("/api/v1")
        ).andExpect(status().isInternalServerError()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void getAllEventsByUserEmailIdReturnResponseTest() throws Exception {
        when(eventService.getAllEventsByUserEmailId(any(String.class))).thenReturn(new ArrayList<>(List.of(event)));
        mockMvc.perform(
                get("/api/v1/1011")
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
    }
    @Test
    public void getAllEventsByUserEmailIdThrowUserNotFoundExceptionTest() throws Exception {
        when(eventService.getAllEventsByUserEmailId(any(String.class))).thenThrow(UserNotFoundException.class);
        mockMvc.perform(
                get("api/v1/1011")
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.log());
    }
}

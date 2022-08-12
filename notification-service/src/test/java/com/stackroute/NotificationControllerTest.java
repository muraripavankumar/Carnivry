
package com.stackroute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.controller.EmailController;
import com.stackroute.model.*;
import com.stackroute.service.EmailService;
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
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {
    @Mock
    private EmailService emailService;
    @InjectMocks
    private EmailController emailController;
    @Autowired
    MockMvc mockMvc;

    private ProducerEmail producerEmail;
    private ConsumerEmail consumerEmail;
    private MailResponse mailResponse;

    @BeforeEach
    public void beforeit(){
        mockMvc= MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @AfterEach
    public void conclude(){
        producerEmail=null;
        consumerEmail=null;
    }
    private static String convertToJson(final Object obj){
        String result="";
        try {
            ObjectMapper mapper=new ObjectMapper();
            result=mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
     public void sendEventEmailToProducer(){
        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
        Venue venue=new Venue("Test venue name",address);
        producerEmail=new ProducerEmail("muraripavankumar24@gmail.com","title1","name1",eventTiming,venue,250);
        mailResponse=new MailResponse("mail sent to producer successfully",true);
        when(emailService.mailToProducer(producerEmail)).thenReturn(mailResponse);
        try {
            mockMvc.perform(
                    post("/api/v1/sendingEmailToProducer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertToJson(producerEmail))
            ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendTicketEmailToConsumer(){
        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
        Venue venue=new Venue("Test venue name",address);
        ArrayList<Seat> seatArrayList=new ArrayList<>();
        Seat seat1=new Seat(5,6,1,new BigDecimal("100.05"),"NOT_BOOKED","common");
        Seat seat2=new Seat(5,6,2,new BigDecimal("200.05"),"NOT_BOOKED","common");
        seatArrayList.add(seat1);
        seatArrayList.add(seat2);

        consumerEmail=new ConsumerEmail("muraripavankumar24@gmail.com","title1","description1","name1",eventTiming,venue,new BigDecimal(2000),seatArrayList,25);

        mailResponse=new MailResponse("mail sent to consumer successfully",true);
        when(emailService.mailToConsumer(consumerEmail)).thenReturn(mailResponse);
        try {
            mockMvc.perform(
                    post("/api/v1/sendingEmailToConsumer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertToJson(consumerEmail))
            ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//package com.stackroute;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stackroute.controller.EmailController;
//import com.stackroute.model.*;
//import com.stackroute.service.EmailService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class NotificationControllerTest {
//    @Mock
//    private EmailService emailService;
//    @InjectMocks
//    private EmailController emailController;
//    @Autowired
//    MockMvc mockMvc;
//
//    private ProducerEmail producerEmail;
//    private ConsumerEmail consumerEmail;
//    private MailResponse mailResponse;
//
//    @BeforeEach
//    public void beforeit(){
//        mockMvc= MockMvcBuilders.standaloneSetup(emailController).build();
//    }
//
//    @AfterEach
//    public void conclude(){
//        producerEmail=null;
//        consumerEmail=null;
//    }
//    private static String convertToJson(final Object obj){
//        String result="";
//        try {
//            ObjectMapper mapper=new ObjectMapper();
//            result=mapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @Test
//     public void sendEventEmailToProducer(){
//        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
//        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
//        Venue venue=new Venue("Test venue name",address);
//        producerEmail=new ProducerEmail("muraripavankumar24@gmail.com","title1","name1",eventTiming,venue,250);
//        mailResponse=new MailResponse("mail sent to producer successfully",true);
//        when(emailService.mailToProducer(producerEmail)).thenReturn(mailResponse);
//        try {
//            mockMvc.perform(
//                    post("/api/v1/sendingEmailToProducer")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(convertToJson(producerEmail))
//            ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void sendTicketEmailToConsumer(){
//        EventTiming eventTiming=new EventTiming(new Date(),new Date(),"11:24","04:06");
//        Address address=new Address("23","Test street","Test landmark","Test city","Test state","Test Country",123456);
//        Venue venue=new Venue("Test venue name",address);
//        ArrayList<Seat> seatArrayList=new ArrayList<>();
//        Seat seat1=new Seat(5,6,1,new BigDecimal("100.05"),"NOT_BOOKED","common");
//        Seat seat2=new Seat(5,6,2,new BigDecimal("200.05"),"NOT_BOOKED","common");
//        seatArrayList.add(seat1);
//        seatArrayList.add(seat2);
//        consumerEmail=new ConsumerEmail("muraripavankumar24@gmail.com","title1","description1","name1",eventTiming,venue,new BigDecimal(2000),seatArrayList);
//        mailResponse=new MailResponse("mail sent to consumer successfully",true);
//        when(emailService.mailToConsumer(consumerEmail)).thenReturn(mailResponse);
//        try {
//            mockMvc.perform(
//                    post("/api/v1/sendingEmailToConsumer")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(convertToJson(consumerEmail))
//            ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.log());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}


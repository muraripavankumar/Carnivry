package com.stackroute;

import com.stackroute.controller.UserController;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.User;
import com.stackroute.service.SecurityTokenGenerator;
import com.stackroute.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)

public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private SecurityTokenGenerator securityTokenGenerator;

    private User user;

    @BeforeEach
    public void setUp(){
        user=new User("pavan@1","honey24");
        mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public  void destroy(){
        user=null;
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
    public void postingUserData() throws Exception {
        when(userService.saveUser(user)).thenReturn(user);
        mockMvc.perform(
                        post("/api/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(user);
    }

    @Test
    public void postingUserDataReturnsException() throws Exception {
        when(userService.saveUser(user)).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(
                        post("/api/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(user);
    }
    @Test
    public void UpdatingUserData() throws Exception {
        when(userService.forgotPassword(user)).thenReturn(user);
        mockMvc.perform(
                        put("/api/v1/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).forgotPassword(user);
    }
    @Test
    public void updatingUserDataReturnsException() throws Exception {
        when(userService.forgotPassword(user)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(
                        put("/api/v1/reset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).forgotPassword(user);
    }

    @Test
    public void AuthenticateUser() throws Exception {
        when(userService.authenticateUser(user.getEmail(),user.getPassword())).thenReturn(user);
        mockMvc.perform(
                        post("/api/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).authenticateUser(user.getEmail(),user.getPassword());
    }

    @Test
    public void resetPassword() throws Exception {
        when(userService.forgotPassword(user)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(
                put("/api/v1/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).forgotPassword(user);
    }

    @Test
    public void forgotPassword() throws Exception {
        when(userService.getUser(user.getEmail())).thenReturn(user);
        mockMvc.perform(
                        post("/api/v1/forgot-password/{email}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).getUser(user.getEmail());
    }




}

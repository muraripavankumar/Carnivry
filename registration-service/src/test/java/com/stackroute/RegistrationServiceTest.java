package com.stackroute;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.model.UserRegModel;
import com.stackroute.rabbitMQ.AuthenticationUserDTO;
import com.stackroute.rabbitMQ.MessageProducer;
import com.stackroute.repository.UserRepository;
import com.stackroute.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    MessageProducer messageProducer;

    @InjectMocks
    UserServiceImpl userService;

    CarnivryUser carnivryUser;
    UserRegModel userRegModel;
    AuthenticationUserDTO authenticationUserDTO;


    @BeforeEach
    public void initialize(){

        userRegModel = new UserRegModel();
        userRegModel.setEmail("abc@gmail.com");
        userRegModel.setName("abc xyz");
        userRegModel.setPassword("abc@Carnivry");
        userRegModel.setMatchingPassword("abc@Carnivry");

        carnivryUser= new CarnivryUser();
        carnivryUser.setEmail(userRegModel.getEmail());
        carnivryUser.setName(userRegModel.getName());
        carnivryUser.setVerified(false);


    }

    @AfterEach
    public void clean(){
        carnivryUser= null;
        userRegModel=null;
    }

    @Test
    public  void addUserSuccess() throws UserAlreadyExistsException {
        authenticationUserDTO= new AuthenticationUserDTO(userRegModel.getEmail(), userRegModel.getPassword());

        when(userRepository.existsById(userRegModel.getEmail())).thenReturn(false);
        when(userRepository.save(any(CarnivryUser.class))).thenReturn(carnivryUser);
//        when(messageProducer.sendMessageToAuthenticationService(any(AuthenticationUserDTO.class))).thenReturn(1);

        CarnivryUser result= userService.registerSocialUser(userRegModel);

        assertEquals(carnivryUser.getEmail(),result.getEmail());

//        verify(userRepository,times(1)).findByEmail(carnivryUser.getEmail());
//        verify(userRepository,times(1)).save(carnivryUser);
    }

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void addSocialUserSuccess() throws UserAlreadyExistsException {
        when(userRepository.existsById(userRegModel.getEmail())).thenReturn(false);
        when(userRepository.save(any(CarnivryUser.class))).thenReturn(carnivryUser);


        CarnivryUser result= userService.registerSocialUser(userRegModel);

        assertEquals(carnivryUser.getEmail(),result.getEmail());
    }
}

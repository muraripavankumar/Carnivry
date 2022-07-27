package com.stackroute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.controller.RegistrationController;
import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.AddGenre;
import com.stackroute.model.Genre;
import com.stackroute.model.UserRegModel;
import com.stackroute.model.UserRegResponseModel;
import com.stackroute.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Flow;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    RegistrationController controller;

    @Autowired
    MockMvc mvc;

    @Mock
    ApplicationEventPublisher publisher;

    CarnivryUser carnivryUser;
    UserRegModel userRegModel;

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

        mvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    public void clean(){
        carnivryUser= null;
        userRegModel=null;
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public  void registrationSuccess() throws Exception {
        when(userService.registerUser(userRegModel)).thenReturn(carnivryUser);
        doNothing().when(publisher).publishEvent(any());

        mvc.perform(
                        post("/api/v1/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(userRegModel)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void registrationFailure() throws Exception {
        when(userService.registerUser(userRegModel)).thenThrow(new UserAlreadyExistsException());

        mvc.perform(
                        post("/api/v1/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(userRegModel)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).registerUser(userRegModel);
    }

    @Test
    public  void registrationSocialUserSuccess() throws Exception {
        when(userService.registerSocialUser(userRegModel)).thenReturn(carnivryUser);

        mvc.perform(
                        post("/api/v1/registration/socialLogin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(userRegModel)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).registerSocialUser(userRegModel);
    }
    @Test
    public  void registrationSocialUserFailure() throws Exception {
        when(userService.registerSocialUser(userRegModel)).thenThrow(new UserAlreadyExistsException());

        mvc.perform(
                        post("/api/v1/registration/socialLogin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(userRegModel)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).registerSocialUser(userRegModel);
    }

    @Test
    public void verifyRegistrationInvalidToken() throws Exception {
        String token= UUID.randomUUID().toString();
        when(userService.validateVerificationToken(token,userRegModel.getEmail()))
                .thenReturn("invalid token");

        mvc.perform(
                        get("/api/v1/verifyRegistration?token="+ token + "&email="+ userRegModel.getEmail()))
                .andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).validateVerificationToken(token,userRegModel.getEmail());
    }

    @Test
    public void verifyRegistrationTokenExpired() throws Exception {
        String token= UUID.randomUUID().toString();
        when(userService.validateVerificationToken(token,userRegModel.getEmail()))
                .thenReturn("token expired");

        mvc.perform(
                        get("/api/v1/verifyRegistration?token="+ token + "&email="+ userRegModel.getEmail()))
                .andExpect(status().isRequestTimeout()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).validateVerificationToken(token,userRegModel.getEmail());
    }

    @Test
    public void verifyRegistrationTokenValidToken() throws Exception {
        String token= UUID.randomUUID().toString();
        when(userService.validateVerificationToken(token,userRegModel.getEmail()))
                .thenReturn("valid token");

        mvc.perform(
                        get("/api/v1/verifyRegistration?token="+ token + "&email="+ userRegModel.getEmail()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).validateVerificationToken(token,userRegModel.getEmail());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void saveGenresSuccess() throws Exception {
        AddGenre addGenre= new AddGenre(userRegModel.getEmail(), List.of("Music","Religion","Art"));
       doNothing().when(userService).addLikedGenres(addGenre);

        mvc.perform(
                        post("/api/v1/saveGenres")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(addGenre)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).addLikedGenres(any(AddGenre.class));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void saveGenreFailure() throws Exception {
        AddGenre addGenre= new AddGenre(userRegModel.getEmail(), List.of("Music","Religion","Art"));
        doThrow(new UserNotFoundException()).doNothing().when(userService).addLikedGenres(any(AddGenre.class));

        mvc.perform(
                        post("/api/v1/saveGenres")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(addGenre)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).addLikedGenres(any(AddGenre.class));

    }

    @Test
    public void getAllGenres() throws Exception {
        List<Genre> genres= List.of(Genre.values());
        List<String> genreString= new ArrayList<>();
        for (Genre genre: genres) {
            genreString.add(genre.toString());
        }
        when(userService.getAllGenres()).thenReturn(genreString);

        mvc.perform(
                        get("/api/v1/allGenres"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).getAllGenres();
    }

    @Test
    public void emailVerificationStatusSuccess() throws Exception {
        when(userService.isUserVerified(userRegModel.getEmail())).thenReturn(carnivryUser.getVerified());

        mvc.perform(
                get("/api/v1//emailVerifiedStatus/"+carnivryUser.getEmail()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).isUserVerified(carnivryUser.getEmail());

    }

    @Test
    public void emailVerificationStatusFailure() throws Exception {
        when(userService.isUserVerified(carnivryUser.getEmail())).thenThrow(new UserNotFoundException());

        mvc.perform(
                        get("/api/v1/emailVerifiedStatus/"+carnivryUser.getEmail()))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).isUserVerified(carnivryUser.getEmail());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void resendEmailVerTokenSuccess() throws Exception {
        doNothing().when(userService)
                .regenerateEmailVerificationToken(carnivryUser.getEmail(),"http://localhost:8080");

        mvc.perform(
                        get("/api/v1/resendVerificationToken?email="+carnivryUser.getEmail()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1))
                .regenerateEmailVerificationToken(carnivryUser.getEmail(), "http://localhost:80");
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void resendEmailVerTokenFailure() throws Exception {
        doThrow(new UserNotFoundException()).doNothing().when(userService)
                .regenerateEmailVerificationToken(carnivryUser.getEmail(),"http://localhost:80");

        mvc.perform(
                        get("/api/v1/resendVerificationToken?email="+carnivryUser.getEmail()))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1))
                .regenerateEmailVerificationToken(carnivryUser.getEmail(), "http://localhost:80");
    }

    @Test
    public void isUserPresent() throws Exception {
        when(userService.isUserPresent(carnivryUser.getEmail())).thenReturn(true);

        mvc.perform(
                        get("/api/v1/userCheck/"+carnivryUser.getEmail()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

        verify(userService,times(1)).isUserPresent(carnivryUser.getEmail());
    }


    private static String convertToJson(final Object obj)
    {
        String result="";
        try {
            ObjectMapper mapper= new ObjectMapper();
            result= mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}

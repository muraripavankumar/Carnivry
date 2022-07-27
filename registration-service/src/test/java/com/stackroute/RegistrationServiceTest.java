package com.stackroute;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.AddGenre;
import com.stackroute.model.EmailRequest;
import com.stackroute.model.UserRegModel;
import com.stackroute.rabbitMQ.AuthenticationUserDTO;
import com.stackroute.rabbitMQ.MessageProducer;
import com.stackroute.repository.UserRepository;
import com.stackroute.service.EmailSenderService;
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

import java.util.Date;
import java.util.List;
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

    @Mock
    EmailSenderService emailSenderService;

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

        verify(userRepository,times(1)).existsById(carnivryUser.getEmail());
        verify(userRepository,times(1)).save(any(CarnivryUser.class));
    }

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void addSocialUserSuccess() throws UserAlreadyExistsException {
        when(userRepository.existsById(userRegModel.getEmail())).thenReturn(false);
        when(userRepository.save(any(CarnivryUser.class))).thenReturn(carnivryUser);


        CarnivryUser result= userService.registerSocialUser(userRegModel);

        assertEquals(carnivryUser.getEmail(),result.getEmail());

        verify(userRepository,times(1)).existsById(carnivryUser.getEmail());
        verify(userRepository,times(1)).save(any(CarnivryUser.class));
    }

    @Test
    public void addUserFailure(){
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.ofNullable(carnivryUser));

        assertThrows(UserAlreadyExistsException.class,()->userService.registerUser(userRegModel));

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
    }

    @Test
    public void addSocialUserFailure(){
        when(userRepository.existsById(userRegModel.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,()->userService.registerSocialUser(userRegModel));

        verify(userRepository,times(1)).existsById(userRegModel.getEmail());
    }

    @Test
    public void getAllGenres(){
        assertEquals(19,userService.getAllGenres().size());
    }

    @Test
    public void addGenresSuccess() throws UserNotFoundException {
        AddGenre addGenre= new AddGenre();
        addGenre.setEmail(userRegModel.getEmail());
        addGenre.setGenres(List.of("Music","Adventure","Education"));

//        Preferences preferences= new Preferences();
//        preferences.setLikedGenres(Set.of(Genre.MUSIC,Genre.ADVENTURE,Genre.EDUCATION));
//        carnivryUser.setPreferences(preferences);

        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.ofNullable(carnivryUser));
        when(userRepository.save(any(CarnivryUser.class))).thenReturn(carnivryUser);

        userService.addLikedGenres(addGenre);
        assertEquals(3,carnivryUser.getPreferences().getLikedGenres().size());

        verify(userRepository,times(2)).findById(userRegModel.getEmail());
        verify(userRepository,times(1)).save(any(CarnivryUser.class));
    }

    @Test
    public void addGenresFailure(){
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userService.addLikedGenres(new AddGenre(userRegModel.getEmail(),null)));

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
    }

    @Test
    public void saveVerTokenSuccess() throws UserNotFoundException {
        String token= UUID.randomUUID().toString();
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.ofNullable(carnivryUser));
        when(userRepository.save(any(CarnivryUser.class))).thenReturn(carnivryUser);

        userService.saveVerificationTokenForUser(token,carnivryUser);

        assertEquals(token,carnivryUser.getEmailVerificationToken());

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
        verify(userRepository,times(1)).save(any(CarnivryUser.class));
    }

    @Test
    public void saveVerTokenFailure(){
        String token= UUID.randomUUID().toString();
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userService.saveVerificationTokenForUser(token,carnivryUser));

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
    }

    @Test
    public void validateVerTokenInvalidTokenCause1(){
        String token1= UUID.randomUUID().toString();

        when(userRepository.findByEmailVerificationToken(token1)).thenReturn(null);

        assertEquals("invalid token",userService.validateVerificationToken(token1,userRegModel.getEmail()));

        verify(userRepository,times(1)).findByEmailVerificationToken(token1);
    }

    @Test
    public void validateVerTokenInvalidTokenCause2(){
        String token1= UUID.randomUUID().toString();

        when(userRepository.findByEmailVerificationToken(token1)).thenReturn(carnivryUser);

        assertEquals("invalid token",userService.validateVerificationToken(token1,"mno@gmail.com"));

        verify(userRepository,times(1)).findByEmailVerificationToken(token1);
    }

    @Test
    public void validateVerTokenTimeExpired(){
        String token= UUID.randomUUID().toString();
        carnivryUser.setEvtExpTime(new Date());
        carnivryUser.setEmailVerificationToken(token);

        when(userRepository.findByEmailVerificationToken(token)).thenReturn(carnivryUser);

        assertEquals("token expired",userService.validateVerificationToken(token,carnivryUser.getEmail()));

        verify(userRepository,times(1)).findByEmailVerificationToken(token);
    }

    @Test
    public void validateVerTokenValidToken(){
        String token= UUID.randomUUID().toString();
        carnivryUser.setEvtExpTime(new Date(new Date().getTime()+5000));
        carnivryUser.setEmailVerificationToken(token);

        when(userRepository.findByEmailVerificationToken(token)).thenReturn(carnivryUser);

        assertEquals("valid token",userService.validateVerificationToken(token,carnivryUser.getEmail()));

        verify(userRepository,times(1)).findByEmailVerificationToken(token);
    }

    @Test
    public void isUserVerifiedSuccess() throws UserNotFoundException {
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.ofNullable(carnivryUser));
        assertFalse(userService.isUserVerified(userRegModel.getEmail()));

        verify(userRepository,times(2)).findById(userRegModel.getEmail());
    }

    @Test
    public void isUserVerifiedFailure(){
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.isUserVerified(userRegModel.getEmail()));

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
    }

    @Test
    public void regenerateEVTSuccess() throws UserNotFoundException {
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.ofNullable(carnivryUser));
        when(userRepository.save(any(CarnivryUser.class))).thenReturn(carnivryUser);
        when(emailSenderService.sendEmailWithAttachment(any(EmailRequest.class), any())).thenReturn("Email Send...");

        userService.regenerateEmailVerificationToken(userRegModel.getEmail(),"");

        assertNotNull(carnivryUser.getEmailVerificationToken());
    }

    @Test
    public void regenerateEVTFailure(){
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userService.regenerateEmailVerificationToken(userRegModel.getEmail(),""));

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
    }

    @Test
    public void isUserPresent(){
        when(userRepository.findById(userRegModel.getEmail())).thenReturn(Optional.empty());

        assertFalse(userService.isUserPresent(userRegModel.getEmail()));

        verify(userRepository,times(1)).findById(userRegModel.getEmail());
    }
}

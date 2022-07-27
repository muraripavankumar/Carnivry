package com.example.SuggestionService.ServiceTest;

import com.example.SuggestionService.Respository.EventsRepo;
import com.example.SuggestionService.Respository.UserRepo;
import com.example.SuggestionService.Services.EventService;
import com.example.SuggestionService.Services.UserService;
import com.example.SuggestionService.entity.Events;
import com.example.SuggestionService.entity.User;
import com.example.SuggestionService.exception.UserAlreadyExistException;
import com.example.SuggestionService.exception.UserNotfoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTesting {

    @Mock
    private UserRepo userRepo;
    @Mock
    private EventsRepo eventsRepo;

    @InjectMocks
    UserService userService;

    User user;

    @BeforeEach
    public void setUp(){
        user=new User();
        List<String> wishlist= new ArrayList<>();
        List<String> likedGenre= new ArrayList<>();
        likedGenre.add("Action");
        likedGenre.add("Sports");

        user.setEmailId("gita@gmail.com");
        user.setName("Gita");
        user.setWishlist(wishlist);
        user.setLikedGenre(likedGenre);
        user.setHouseNo("201");
        user.setStreet("Street 1");
        user.setLandmark("Landmark 1");
        user.setCity("Jodhpur");
        user.setState("Rajasthan");
        user.setPincode(342005);
    }

    @Test
    public void addUserTesting() throws UserAlreadyExistException {
        User user1= userService.addUser(user);
        assertEquals(user1.getEmailId(),user.getEmailId());
    }

    @Test
    public void getAllUsersTesting() throws UserNotfoundException, UserAlreadyExistException {
        userService.addUser(user);
        List<User> allUsers= userService.getAllUsers();
        assertEquals(5, allUsers.size());
    }


}

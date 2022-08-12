package com.stackroute.RepositoryTest;


import com.stackroute.Respository.UserRepo;
import com.stackroute.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataNeo4jTest
public class UserRepoTesting {

    UserRepo userRepo;

    @Autowired
    public UserRepoTesting(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

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
//        user.setHouseNo("201");
//        user.setStreet("Street 1");
//        user.setLandmark("Landmark 1");
        user.setCity("Jodhpur");
//        user.setState("Rajasthan");
//        user.setPincode(342005);
    }

    @AfterEach
    public void afterTesting(){
        userRepo.deleteAll();
    }

    @Test
    public void userSaveTesting(){
        userRepo.save(user);
        User user1=userRepo.findById(user.getEmailId()).get();
        assertNotNull(user1);
        assertEquals(user.getEmailId(),user1.getEmailId());
    }

    @Test
    public void getAllEvents(){
        userRepo.save(user);
        List<User> allUser= userRepo.findAll();
        assertEquals(6, allUser.size());
    }

    @Test
    public void getEventById(){
        userRepo.save(user);
        User user1= userRepo.findById(user.getEmailId()).get();
        assertEquals("gita@gmail.com", user1.getEmailId());
    }

}

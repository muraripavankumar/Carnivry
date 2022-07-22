package com.stackroute;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    UserRepository userRepository;

    CarnivryUser carnivryUser;


    @BeforeEach
    public void initialize(){
        userRepository.deleteAll();

        carnivryUser= new CarnivryUser();
        carnivryUser.setEmail("abc@gmail.com");
        carnivryUser.setName("abc xyz");
        carnivryUser.setVerified(false);
    }

    @AfterEach
    public void clean(){
        carnivryUser= null;

    }

    @Test
    public void saveProduct()
    {
        userRepository.save(carnivryUser);
        CarnivryUser result= userRepository.findById(carnivryUser.getEmail()).get();
        assertEquals(carnivryUser.getEmail(),result.getEmail());
    }



    @Test
    public void updateUser(){
        userRepository.save(carnivryUser);
        CarnivryUser result= userRepository.findById(carnivryUser.getEmail()).get();
        result.setVerified(true);
        userRepository.save(result);
        result= userRepository.findById(carnivryUser.getEmail()).get();
        assertEquals(true,result.getVerified());
    }
}

package com.stackroute;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.model.User;
import com.stackroute.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
   @Autowired
   private UserRepository userRepository;

   private User user;

    @BeforeEach
    public void setUp(){
        user = new User("pavan@1","honey24");
    }

    @AfterEach
    public  void destroy(){
        user=null;
       userRepository.deleteAll();
    }

    @Test

    public void saveReturnsUser() {
        userRepository.save(user);
        User user1=userRepository.findById(user.getEmail()).get();
        //assertThat(user1).isEqualTo(user);
        assertEquals(user1.getEmail(),user.getEmail());
    }

    @Test
    public  void findByEmailAndPasswordUser(){
        userRepository.save(user);
       User user1= userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        assertEquals(user1.getEmail(),user.getEmail());
        assertEquals(user1.getPassword(),user.getPassword());
    }

    @Test
    public  void findByEmail(){
        userRepository.save(user);
        User user1= userRepository.findByEmail(user.getEmail());
        assertEquals(user1.getEmail(),user.getEmail());
        assertEquals(user1.getPassword(),user.getPassword());
    }



}

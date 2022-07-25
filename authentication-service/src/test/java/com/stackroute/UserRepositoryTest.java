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
        User user1=userRepository.findById(user.getEmailId()).get();
        //assertThat(user1).isEqualTo(user);
        assertEquals(user1.getEmailId(),user.getEmailId());
    }

    @Test
    public  void findByEmailAndPasswordUser(){
        userRepository.save(user);
       User user1= userRepository.findByEmailIdAndPassword(user.getEmailId(),user.getPassword());
        assertEquals(user1.getEmailId(),user.getEmailId());
        assertEquals(user1.getPassword(),user.getPassword());
    }

    @Test
    public  void findByEmail(){
        userRepository.save(user);
        User user1= userRepository.findByEmailId(user.getEmailId());
        assertEquals(user1.getEmailId(),user.getEmailId());
        assertEquals(user1.getPassword(),user.getPassword());
    }



}

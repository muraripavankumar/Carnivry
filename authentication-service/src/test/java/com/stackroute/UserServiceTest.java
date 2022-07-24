package com.stackroute;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.User;
import com.stackroute.repository.UserRepository;

import com.stackroute.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp(){
        user=new User("pavan@1","honey24");
    }

    @AfterEach
    public  void destroy(){
        user=null;
    }

    @Test
    public void givenUserToSave() throws UserAlreadyExistsException{
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user,userService.saveUser(user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void givenUserToSaveReturnsException(){
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserAlreadyExistsException.class, ()-> userService.saveUser(user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(0)).save(user);
    }

    @Test
    public void givenUserToUpdate() throws UserNotFoundException {
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user,userService.resetPassword(user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void givenUserToUpdateReturnsException(){
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class, ()-> userService.resetPassword(user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(0)).save(user);
    }

    @Test
    public void givenUserForAuthentication() throws UserNotFoundException {
        when(userRepository.findByEmailIdAndPassword(user.getEmailId(),user.getPassword())).thenReturn(user);
        assertEquals(user,
                userService.authenticateUser(user.getEmailId(),user.getPassword()));
        verify(userRepository,times(1)).findByEmailIdAndPassword(user.getEmailId(),user.getPassword());

    }

    @Test
    public void givenUserForAuthenticationReturnsException() {
        when(userRepository.findByEmailIdAndPassword(user.getEmailId(),user.getPassword())).thenReturn(null);
        assertThrows(UserNotFoundException.class,()-> userService.authenticateUser(user.getEmailId(),user.getPassword()));
        verify(userRepository,times(1)).findByEmailIdAndPassword(user.getEmailId(),user.getPassword());

    }
}

package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.User;

import java.util.List;

public interface UserService {
     User saveUser(User user) throws UserAlreadyExistsException;
     User authenticateUser(String EmailId,String pwd) throws UserNotFoundException;
     List<User> getAllUsers();
     User  getUser (String emailId)throws UserNotFoundException;
    User resetPassword(User user) throws UserNotFoundException;
     User forgotPassword(String emailId) throws UserNotFoundException;
}

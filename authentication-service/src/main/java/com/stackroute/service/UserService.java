package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user) ;
    User authenticateUser(String email,String password) throws UserNotFoundException;
    List<User> getAllUsers();
    User  getUser (String email)throws UserNotFoundException;
    User forgotPassword(User user) throws UserNotFoundException;
    User emailLink(String email) throws UserNotFoundException;
}

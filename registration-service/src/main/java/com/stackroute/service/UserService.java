package com.stackroute.service;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.AddGenre;
import com.stackroute.model.UserRegModel;

import java.util.List;

public interface UserService {
    CarnivryUser registerUser(UserRegModel userModel) throws UserAlreadyExistsException;

    void saveVerificationTokenForUser(String token, CarnivryUser demoUser) throws UserNotFoundException;

    String validateVerificationToken(String token, String email);

    boolean isUserVerified(String email);

    void regenerateEmailVerificationToken(String email, String applicationUrl);

    boolean isUserPresent(String email);

    CarnivryUser registerSocialUser(UserRegModel userRegModel) throws UserAlreadyExistsException;

    void addLikedGenres(AddGenre addGenre) throws UserNotFoundException;

    List<String> getAllGenres();


}
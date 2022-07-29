package com.stackroute.service;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;

import java.util.List;

public interface UserService {
    CarnivryUser registerUser(UserRegModel userModel) throws UserAlreadyExistsException;

    void saveVerificationTokenForUser(String token, CarnivryUser demoUser) throws UserNotFoundException;

    String validateVerificationToken(String token, String email);

    boolean isUserVerified(String email) throws UserNotFoundException;

    void regenerateEmailVerificationToken(String email, String applicationUrl) throws UserNotFoundException;

    boolean isUserPresent(String email);

    CarnivryUser registerSocialUser(UserRegModel userRegModel) throws UserAlreadyExistsException;

    void addLikedGenres(AddGenre addGenre) throws UserNotFoundException;

    List<String> getAllGenres();


    void saveProfilePic(AddProfilePic addProfilePic) throws UserNotFoundException;

    String getUsername(String email) throws UserNotFoundException;

    boolean sendOTPForPhoneNoVerification(PhoneNoValidationRequestDto phoneNoValidationRequestDto) throws UserNotFoundException;

    String validatePhoneVerificationOTP(PhoneNoValidationRequestDto phoneNoValidationRequestDto) throws UserNotFoundException;

    void saveDOB(AddDOB addDOB) throws UserNotFoundException;

    void saveAddress(AddAddress addAddress) throws UserNotFoundException;
}
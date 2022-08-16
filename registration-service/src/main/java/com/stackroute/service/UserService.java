package com.stackroute.service;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;
import com.stackroute.rabbitMQ.TicketDTO;

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

//    boolean sendOTPForPhoneNoVerification(PhoneNoValidationRequestDto phoneNoValidationRequestDto) throws UserNotFoundException;

//    String validatePhoneVerificationOTP(PhoneNoValidationRequestDto phoneNoValidationRequestDto) throws UserNotFoundException;

    void saveDOB(AddDOB addDOB) throws UserNotFoundException;

    void saveAddress(AddAddress addAddress) throws UserNotFoundException;

    String getProfilePicture(String email) throws UserNotFoundException;

    void sendNewEmailVerificationToken(AddEmail addEmail, String applicationUrl) throws UserNotFoundException;

    String verifyNewEmail(String token, String oldEmail, String newEmail);

    boolean isNewEmailVerified(AddEmail addEmail) throws UserNotFoundException;

//    void savePostedEvent(String email, Event postedEvent) throws UserNotFoundException;


//    List<Event> getPostedEvent(String email) throws UserNotFoundException;

    List<String> getGenres(String email) throws UserNotFoundException;

    void saveEventToWishlist(AddWishlist addWishlist) throws UserNotFoundException;

    void saveBookedTickets(TicketDTO ticket) ;

    void sendDataToSuggestionService(CarnivryUser carnivryUser);

    List<TicketDTO> getPastEvents(String email) throws UserNotFoundException;

    List<TicketDTO> getUpcomingEvents(String email) throws UserNotFoundException;

    List<Event> getWishlist(String email) throws UserNotFoundException;
}
package com.stackroute.controller;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.event.RegistrationCompleteEvent;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;
import com.stackroute.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody UserRegModel userRegModel, final HttpServletRequest request){
        try {
            CarnivryUser carnivryUser= userService.registerUser(userRegModel);

            publisher.publishEvent(new RegistrationCompleteEvent(carnivryUser,applicationUrl(request)));

            UserRegResponseModel regResponseModel= new UserRegResponseModel();
            regResponseModel.setEmail(carnivryUser.getEmail());
            regResponseModel.setName(carnivryUser.getName());



            return new ResponseEntity<UserRegResponseModel>(regResponseModel, HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e)
        {
            log.error("Couldn't add user since user already exists");
            return new ResponseEntity<>("Couldn't add user since user already exists",HttpStatus.CONFLICT);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/registration/socialLogin")
    public ResponseEntity<?> addSocialUser(@RequestBody UserRegModel userRegModel){
        try {
            CarnivryUser carnivryUser= userService.registerSocialUser(userRegModel);
            UserRegResponseModel userRegResponseModel= new UserRegResponseModel(carnivryUser.getName(), carnivryUser.getEmail());
            return new ResponseEntity<UserRegResponseModel>(userRegResponseModel,HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e)
        {
            log.error("Couldn't add user since user already exists");
            return new ResponseEntity<>("Couldn't add user since user already exists",HttpStatus.CONFLICT);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token, @RequestParam("email") String email) {

        try {
            String result = userService.validateVerificationToken(token, email);
            if (result.equalsIgnoreCase("valid token")) {
                log.info("User Verified");
                return new ResponseEntity<>("Email: " + email + " verified Successfully. Close this tab and visit Carnivry"
                        , HttpStatus.OK);
            } else if (result.equalsIgnoreCase("token expired")) {
                log.error("Email Verification Token Expired");
                return new ResponseEntity<>("Your 10 minutes up, Ask for resend email and verify within 10 minutes." +
                        " Close this tab and visit Carnivry", HttpStatus.REQUEST_TIMEOUT);
            } else {
                log.error("Invalid Email Verification Token");
                return new ResponseEntity<>("Bad User", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveGenres")
    public  ResponseEntity<String> addUserGenres(@RequestBody AddGenre addGenre ){
        try{
            userService.addLikedGenres(addGenre);
            log.info("Genres added to email id {}",addGenre.getEmail());
            return new ResponseEntity<>("Genres added",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",addGenre.getEmail());
            return new ResponseEntity<>("User not found with email id "+addGenre.getEmail(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/dobAddition")
    public  ResponseEntity<String> addDOB(@RequestBody AddDOB addDOB ){
        try{
            userService.saveDOB(addDOB);
            log.info("DOB of user with email id {} saved",addDOB.getEmail());
            return new ResponseEntity<>("DOB saved",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",addDOB.getEmail());
            return new ResponseEntity<>("User not found with email id "+addDOB.getEmail(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addressAddition")
    public  ResponseEntity<String> addAddress(@RequestBody AddAddress addAddress ){
        try{
            userService.saveAddress(addAddress);
            log.info("Address of user with email id {} saved",addAddress.getEmail());
            return new ResponseEntity<>("Address saved",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",addAddress.getEmail());
            return new ResponseEntity<>("User not found with email id "+addAddress.getEmail(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/allGenres")
    public ResponseEntity<?> getAllGenres()
    {
        try {
            log.debug("All Genres Fetched");
            return new ResponseEntity<>(userService.getAllGenres(), HttpStatus.OK);
        }
        catch (Exception e){
        log.error("Internal server error {}",e.getMessage());
        return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/genres/{email}")
    public ResponseEntity<?> getGenres(@PathVariable String email)
    {
        try {
            log.debug("User Genres Fetched");
            return new ResponseEntity<List<String>>(userService.getGenres(email), HttpStatus.OK);
        }catch (UserNotFoundException e){
            log.error("User with email id {} not found",email);
            return new ResponseEntity<>("User with email id "+email+" doesn't exists",HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/emailVerifiedStatus/{email}")
    public ResponseEntity<?> isEmailVerified(@PathVariable String email){
        try {
            log.debug("User verification status returned");
            return new ResponseEntity<>(userService.isUserVerified(email),HttpStatus.OK);
        }catch (UserNotFoundException e){
            log.error("User with email id {} not found",email);
            return new ResponseEntity<>("User with email id "+email+" doesn't exists",HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/resendVerificationToken")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("email") String email, HttpServletRequest request){
        String applicationUrl= applicationUrl(request);
        try {
            userService.regenerateEmailVerificationToken(email,applicationUrl);
            log.info("Email verification token for email id {} resended",email);
            return new ResponseEntity<>("Verification Email Sent",HttpStatus.OK);
        }catch (UserNotFoundException e){
            log.error("User with email id {} doesn't exists",email);
            return new ResponseEntity<>("User with email id "+email+" not found",HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/userCheck/{email}")
    public ResponseEntity<?> isUserPresent(@PathVariable String email) {
        try {
            log.debug("Email id {} 's existence is checked", email);
            return new ResponseEntity<>(userService.isUserPresent(email), HttpStatus.OK);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProfilePic/{email}")
    public ResponseEntity<?> getProfilePic(@PathVariable String email) {
        try {
            log.debug("Profile picture of user with email id {} is fetched", email);
            return new ResponseEntity<>(userService.getProfilePicture(email), HttpStatus.OK);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/postedEventAddition/{email}")
    public ResponseEntity<?> addPostedEvent(@RequestBody Event postedEvent, @PathVariable String email){
        try{
            userService.savePostedEvent(email, postedEvent);
            log.info("Posted event added to user with email id {}",email);
            return new ResponseEntity<>("Posted Event added",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",email);
            return new ResponseEntity<>("User not found with email id "+email,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPostedEvents/{email}")
    public ResponseEntity<?> getPostedEvents(@PathVariable String email){
        try{
            List<Event> postedEvents=  userService.getPostedEvent(email);
            log.info("Posted events of user with email id {} fetched",email);
            return new ResponseEntity<>(postedEvents,HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",email);
            return new ResponseEntity<>("User not found with email id "+email,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/NewEmailAddition")
    public ResponseEntity<?> addEmail(@RequestBody AddEmail addEmail, HttpServletRequest request){
        String applicationUrl= applicationUrl(request);
        try{
            userService.sendNewEmailVerificationToken(addEmail,applicationUrl);
            log.info("New Email Verification link sent to user with email id {}",addEmail.getOldEmail());
            return new ResponseEntity<>("New Email Verification link sent",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",addEmail.getOldEmail());
            return new ResponseEntity<>("User not found with email id "+addEmail.getOldEmail(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verifyNewEmail")
    public ResponseEntity<?> verifyAndSaveNewEmail(@RequestParam("token") String token
            , @RequestParam("oldEmail") String oldEmail,  @RequestParam("newEmail") String newEmail){
        try {
            String response= userService.verifyNewEmail(token,oldEmail,newEmail);
            if(response.equalsIgnoreCase("invalid token"))
                return  new ResponseEntity<>("Invalid token, try again. Close this tab",HttpStatus.BAD_REQUEST);
            else if (response.equalsIgnoreCase("token expired")) {
                log.error("Email Verification Token Expired");
                return new ResponseEntity<>("Your 10 minutes up, Ask for resend email and verify within 10 minutes." +
                        " Close this tab and visit Carnivry", HttpStatus.REQUEST_TIMEOUT);
            }
            else {
                log.info("New Email Verified");
                return new ResponseEntity<>("Email: " + newEmail + " verified Successfully. Close this tab and visit Carnivry"
                        , HttpStatus.OK);
            }
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/newEmailVerificationStatus")
    public ResponseEntity<?> isNewEmailVerified(@RequestBody AddEmail addEmail){
        try {
            boolean answer= userService.isNewEmailVerified(addEmail);
            return new ResponseEntity<>(answer+"",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",addEmail.getOldEmail());
            return new ResponseEntity<>("User not found with old email id "+addEmail.getOldEmail(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ProfilePicAddition")
    public ResponseEntity<?> addProfilePic(@RequestBody AddProfilePic addProfilePic){
        try{
            userService.saveProfilePic(addProfilePic);
            log.info("Profile picture added to user with email id {}",addProfilePic.getEmail());
            return new ResponseEntity<>("Profile Picture added",HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",addProfilePic.getEmail());
            return new ResponseEntity<>("User not found with email id "+addProfilePic.getEmail(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/username/{email}")
    public ResponseEntity<?> getUsername(@PathVariable String email){
        try {
            String name= userService.getUsername(email);
            log.info("Username fetched for user with email id {}",email);
            return new ResponseEntity<>(name,HttpStatus.OK);
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",email);
            return new ResponseEntity<>("User not found with email id "+email,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/PhoneNumberAddition")
    public ResponseEntity<?> addPhoneNumber(@RequestBody PhoneNoValidationRequestDto phoneNoValidationRequestDto){
        try {
            boolean result= userService.sendOTPForPhoneNoVerification(phoneNoValidationRequestDto);
            if (result){
                log.debug("Otp send to phone number {}",phoneNoValidationRequestDto.getPhoneNumber());
                return new ResponseEntity<>("OTP sent to phone number ********"
                        +phoneNoValidationRequestDto.getPhoneNumber().substring(11),HttpStatus.OK);
            }
            else {
                log.debug("Couldn't send OTP to phone number {}",phoneNoValidationRequestDto.getPhoneNumber());
                return new ResponseEntity<>("Couldn't send OTP to phone number ********"
                        +phoneNoValidationRequestDto.getPhoneNumber().substring(11)+" .Try Again."
                        ,HttpStatus.EXPECTATION_FAILED);
            }
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",phoneNoValidationRequestDto.getEmail());
            return new ResponseEntity<>("User not found with email id "+phoneNoValidationRequestDto.getEmail()
                    ,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/PhoneNumberVerificationOTPValidation")
    public ResponseEntity<?> validatePVO(@RequestBody PhoneNoValidationRequestDto phoneNoValidationRequestDto){
        try {
            String result= userService.validatePhoneVerificationOTP(phoneNoValidationRequestDto);
            if (result.equals("Otp expired")){
                log.debug("Otp expired for validating phone number {}",phoneNoValidationRequestDto.getPhoneNumber());
                return new ResponseEntity<>("Your 10 minutes is up. Click on Resent OTP",HttpStatus.REQUEST_TIMEOUT);
            }
            else if(result.equals("Valid otp")){
                log.info("Phone number {} verified and added successfully to user with email id{}."
                        ,phoneNoValidationRequestDto.getPhoneNumber(),phoneNoValidationRequestDto.getEmail());
                return new ResponseEntity<>("Your phone number is verified successfully.",HttpStatus.OK);
            }
            else {
                log.info("Invalid OTP for phone number {}",phoneNoValidationRequestDto.getPhoneNumber());
                return new ResponseEntity<>("Invalid OTP. Please enter correct OTP.",HttpStatus.BAD_REQUEST);
            }
        }catch (UserNotFoundException e)
        {
            log.error("User with email id {} not found",phoneNoValidationRequestDto.getEmail());
            return new ResponseEntity<>("User not found with email id "+phoneNoValidationRequestDto.getEmail()
                    ,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String applicationUrl(HttpServletRequest request) {
//        System.out.println(request.getHeader("Referer"));
        log.debug("application url generated");
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}

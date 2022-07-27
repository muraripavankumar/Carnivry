package com.stackroute.controller;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.event.RegistrationCompleteEvent;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.AddGenre;
import com.stackroute.model.AddProfilePic;
import com.stackroute.model.UserRegModel;
import com.stackroute.model.UserRegResponseModel;
import com.stackroute.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/addingProfilePic")
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

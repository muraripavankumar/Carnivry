package com.stackroute.controller;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.event.RegistrationCompleteEvent;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.AddGenre;
import com.stackroute.model.UserRegModel;
import com.stackroute.model.UserRegResponseModel;
import com.stackroute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/Carnivry")
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<UserRegResponseModel> addUser(@RequestBody UserRegModel userRegModel, final HttpServletRequest request) throws UserAlreadyExistsException {
        try {
            CarnivryUser carnivryUser= userService.registerUser(userRegModel);

            publisher.publishEvent(new RegistrationCompleteEvent(carnivryUser,applicationUrl(request)));

            UserRegResponseModel regResponseModel= new UserRegResponseModel();
            regResponseModel.setEmail(carnivryUser.getEmail());
            regResponseModel.setName(carnivryUser.getName());



            return new ResponseEntity<>(regResponseModel, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            throw new UserAlreadyExistsException();
        }

    }


    @PostMapping("/register/socialLogin")
    public ResponseEntity<UserRegResponseModel> addSocialUser(@RequestBody UserRegModel userRegModel) throws UserAlreadyExistsException {
        try {
            CarnivryUser carnivryUser= userService.registerSocialUser(userRegModel);
            UserRegResponseModel userRegResponseModel= new UserRegResponseModel(carnivryUser.getName(), carnivryUser.getEmail());
            return new ResponseEntity<>(userRegResponseModel,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            throw new UserAlreadyExistsException();
        }
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token, @RequestParam("email") String email) {
        String result = userService.validateVerificationToken(token,email);
        if(result.equalsIgnoreCase("valid token")) {
            return "Email: "+email+" verified Successfully. Close this tab and visit Carnivry";
        }
        else if(result.equalsIgnoreCase("token expired"))
        {
            return "Your 10 minutes up, Ask for resend email and verify within 10 minutes. Close this tab and visit Carnivry";
        }
        return "Bad User";
    }

    @PostMapping("/saveGenres")
    public  String addUserGenres(@RequestBody AddGenre addGenre ) throws UserNotFoundException {
        try{
            userService.addLikedGenres(addGenre);

            return  "Preferences added";
        }catch (Exception e)
        {

            throw new UserNotFoundException();
        }

    }

    @GetMapping("/allGenres")
    public List<String> getAllGenres()
    {
        return userService.getAllGenres();
    }

    @GetMapping("/emailVerifiedStatus/{email}")
    public boolean isEmailVerified(@PathVariable String email)
    {
        return userService.isUserVerified(email);
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("email") String email, HttpServletRequest request) {
        String applicationUrl= applicationUrl(request);
        userService.regenerateEmailVerificationToken(email,applicationUrl);
        return "Verification Email Sent";
    }

    @GetMapping("/checkUser/{email}")
    public boolean isUserPresent(@PathVariable String email) {
        return userService.isUserPresent(email);
    }

    private String applicationUrl(HttpServletRequest request) {
        System.out.println(request.getHeader("Referer"));
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}

package com.stackroute.controller;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.User;
import com.stackroute.service.SecurityTokenGeneratorImpl;
import com.stackroute.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private UserService userService;

    private SecurityTokenGeneratorImpl securityTokenGenerator;

    private JavaMailSender javaMailSender;

    @Autowired
    public UserController(UserService userService, SecurityTokenGeneratorImpl securityTokenGenerator, JavaMailSender javaMailSender) {
        log.info("constructing UserController");
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
        this.javaMailSender = javaMailSender;
    }

//    method for adding new user details to the database
    // http://localhost:64200/api/v1/register   [post]
    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            log.debug("inside registerUser() method");
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException ex){
            log.error("UserAlreadyExistsException in UserController class->registerUser() method");
            throw new UserAlreadyExistsException();
        }
    }

//    method for login to the carnivry application
    // http://localhost:64200/api/v1/login   [post]
    @PostMapping("/login")
    public ResponseEntity<?> loginCheck(@RequestBody User user ) throws UserNotFoundException {
        Map<String, String> map=null;
        try{
            log.debug("inside loginCheck() method");
            User result = userService.authenticateUser(user.getEmailId(),user.getPassword());

            map= securityTokenGenerator.generateToken(result);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        catch(UserNotFoundException ex){
            log.error("UserNotFoundException in UserController class->loginCheck() method");
            throw new UserNotFoundException();
        }
        catch(Exception ex){
            log.error("Exception in UserController class->loginCheck() method");
            return new ResponseEntity<>("Other exception",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:64200/api/v1/getallusers   [get]
    @GetMapping("/getallusers")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }

//    method for fetching userdetails
    // http://localhost:64200/userservice/getuser/{emailId}   [get]
    @GetMapping("/getuser/{emailId}")
    public ResponseEntity<?> getUser(@PathVariable("emailId") String emailId) throws UserNotFoundException {
        try {
            User user=userService.getUser(emailId);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (UserNotFoundException E){
            throw new UserNotFoundException();
        }
    }

//    method for resetting the password if forgotten
    // http://localhost:64200/api/v1/reset   [put]
    @PutMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody User user) throws UserNotFoundException {
        try {
            log.debug("inside resetPassword() method");
            return new ResponseEntity<>(userService.resetPassword(user), HttpStatus.CREATED);
        }
        catch (UserNotFoundException E){
            log.error("UserNotFoundException in UserController class->resetPassword() method");
            throw new UserNotFoundException();
        }
    }

//    method for generating jwttoken when forgotten password
    // http://localhost:64200/api/v1/forgot-password/{emailid}   [post]
    @PostMapping("/forgot-password/{emailId}")
    public ResponseEntity<?> forgotPassword(@PathVariable("emailId") String emailId) throws UserNotFoundException, MessagingException, UnsupportedEncodingException {
        Map<String, String> map=null;
        try{
            log.debug("inside forgotPassword() method");
            User result = userService.forgotPassword(emailId);

            map= securityTokenGenerator.generateToken(result);
//            String str = map.toString();
            String link="http://localhost:4200/Carnivry/updatePassword";
            sendEmail(emailId,link);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        catch(UserNotFoundException ex){
            log.error("UserNotFoundException in UserController class->forgotPassword() method");
            throw new UserNotFoundException();
        }
    }

//    method for sending link of resetting password through gmail
    @Value("${spring.mail.username}") private String sender;
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        log.debug("inside sendEmail() method");
        helper.setFrom(sender);
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }



}


package com.stackroute.event.listner;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.event.RegistrationCompleteEvent;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.service.EmailSenderService;
import com.stackroute.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    public String token;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the User with Link
        CarnivryUser carnivryUser = event.getCarnivryUser();
        token = UUID.randomUUID().toString();
        try {
            userService.saveVerificationTokenForUser(token,carnivryUser);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        //Send Mail to user
        String url =
                event.getApplicationUrl()
                        + "/Carnivry/verifyRegistration?token="
                        + token
                        + "&email="
                        + carnivryUser.getEmail();

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);

        emailSenderService.sendSimpleEmail(carnivryUser.getEmail(),
                "Click the link to verify your account: "+url,
                "Email verification");
    }
}


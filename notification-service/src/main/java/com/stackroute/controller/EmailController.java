package com.stackroute.controller;

import com.stackroute.model.ConsumerEmail;

import com.stackroute.model.MailResponse;
import com.stackroute.model.ProducerEmail;
import com.stackroute.service.EmailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        log.info("constructing EmailController");
        this.emailService = emailService;
    }

    @PostMapping("/sendingEmailToProducer")
    public ResponseEntity<?> sendEmailtoProducer(@RequestBody ProducerEmail producerEmail) throws MessagingException, TemplateException, IOException {
        try {
            log.debug("inside sendEmailtoProducer() method");
            emailService.mailToProducer(producerEmail);
            return new ResponseEntity<>("event details successfully sent to usermail", HttpStatus.OK);
        }catch (Exception ex){
            log.error("Exception in EmailController class->sendEmailtoProducer() method");
            return new ResponseEntity<>("error occurs due to exception case",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sendingEmailToConsumer")
    public ResponseEntity<?> sendEmailtoConsumer(@RequestBody ConsumerEmail consumerEmail) throws MessagingException, TemplateException, IOException {
        try {
            log.debug("inside sendEmailtoConsumer() method");
            emailService.mailToConsumer(consumerEmail);
            return new ResponseEntity<>("booking details have been sent successfullu",HttpStatus.OK);
        }catch (Exception ex){
            log.error("Exception in EmailController class->sendEmailtoConsumer() method");
            return new ResponseEntity<>("error occurs due to exception case",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}

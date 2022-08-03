package com.stackroute.controller;

import com.stackroute.model.ConsumerEmail;
import com.stackroute.model.MailResponse;
import com.stackroute.model.ProducerEmail;
import com.stackroute.service.EmailService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendingEmailToProducer")
    public MailResponse sendEmail(@RequestBody ProducerEmail producerEmail) throws MessagingException, TemplateException, IOException {

       MailResponse mailResponse =emailService.mailToProducer(producerEmail);
        return mailResponse;
    }

    @PostMapping("/sendingEmailToConsumer")
    public MailResponse sendEmail(@RequestBody ConsumerEmail consumerEmail) throws MessagingException, TemplateException, IOException {

        MailResponse mailResponse =emailService.mailToConsumer(consumerEmail);
        return mailResponse;
    }


}

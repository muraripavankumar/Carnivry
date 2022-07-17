package com.stackroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendSimpleEmail(String toEmail,
                                  String body,
                                  String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("saumasischandra987@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        return "Verification Mail Send...";
    }

    public String sendEmailWithAttachment(String toEmail,
                                          String body,
                                          String subject,
                                          String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("saumasischandra987@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystem.getFilename()),
                fileSystem);

        mailSender.send(mimeMessage);
        return "Mail Send...";

    }
}

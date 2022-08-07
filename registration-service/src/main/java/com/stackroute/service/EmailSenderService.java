package com.stackroute.service;

import com.stackroute.model.EmailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final Configuration config;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender, Configuration config) {
        this.mailSender = mailSender;
        this.config = config;
    }

    public String sendSimpleEmail(String toEmail,
                                  String body,
                                  String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("carnivry2022@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        return "Verification Mail Send...";
    }

    public String sendEmailWithAttachment(EmailRequest emailRequest,
                                          Map<String, Object> model){

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template t = config.getTemplate("myHtmlPage.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            mimeMessageHelper.setFrom("carnivry2022@gmail.com");
            mimeMessageHelper.setTo(emailRequest.getTo());
            mimeMessageHelper.setText(html,true);
            mimeMessageHelper.setSubject(emailRequest.getSubject());

//            FileSystemResource fileSystem
//                    = new FileSystemResource(new File(attachment));
//
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystem.getFilename()),
//                    fileSystem);

            mailSender.send(mimeMessage);
            log.info("Email sent to {}",emailRequest.getTo());

        }catch (MessagingException | IOException | TemplateException e){
            log.error("Couldn't send email");
            return "Couldn't send Email...";
        }
        return "Email Send...";

    }
}

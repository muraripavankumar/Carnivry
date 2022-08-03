package com.stackroute.service;

import com.stackroute.model.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService{

    private JavaMailSender javaMailSender;

    private Configuration configuration;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, Configuration configuration) {
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Value("${spring.mail.username}") private String sender;
    @Override
    public MailResponse mailToProducer(ProducerEmail producerEmail) {
        MailResponse mailResponse=new MailResponse();
        MimeMessage message=javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper=new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template template=configuration.getTemplate("producerEmail-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("ProducerName", producerEmail.getEventProducerName());
            model.put("email",producerEmail.getEventProducerEmailId());
            model.put("EventTitle", producerEmail.getEventTitle());
            model.put("StartDate",producerEmail.getEventTimings().getStartDate());
            model.put("EndDate",producerEmail.getEventTimings().getEndDate());
            model.put("StartTime",producerEmail.getEventTimings().getStartTime());
            model.put("EndTime",producerEmail.getEventTimings().getEndTime());
            model.put("venueName",producerEmail.getVenue().getVenueName());
            model.put("houseNumber",producerEmail.getVenue().getAddress().getHouse());
            model.put("Street",producerEmail.getVenue().getAddress().getStreet());
            model.put("LandMark",producerEmail.getVenue().getAddress().getLandmark());
            model.put("city",producerEmail.getVenue().getAddress().getCity());
            model.put("State",producerEmail.getVenue().getAddress().getState());
            model.put("Country",producerEmail.getVenue().getAddress().getCountry());
            model.put("Pincode",producerEmail.getVenue().getAddress().getPincode());
            model.put("TotalSeats",producerEmail.getTotalSeats());
            String html= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);


            helper.setTo(producerEmail.getEventProducerEmailId());
            helper.setText(html,true);
            helper.setSubject("sending host event details");
          //  helper.setFrom(sender);
            javaMailSender.send(message);

            mailResponse.setMessage("mail send to : " + producerEmail.getEventProducerEmailId());
            mailResponse.setStatus(Boolean.TRUE);

        }catch (MessagingException | IOException | TemplateException e) {
            mailResponse.setMessage("Mail Sending failure : "+e.getMessage());
            mailResponse.setStatus(Boolean.FALSE);
        }

        return mailResponse;
    }

    @Override
    public MailResponse mailToConsumer(ConsumerEmail consumerEmail) {
        MailResponse mailResponse=new MailResponse();
        MimeMessage message=javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper=new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template template=configuration.getTemplate("consumerEmail-template.ftl");
            List<Seat> seat=new ArrayList<Seat>();
            seat.addAll(consumerEmail.getBoughtSeats());
            Map<String, Object> model = new HashMap<>();
            model.put("consumerName",consumerEmail.getEventConsumerName());
            model.put("eventTitle",consumerEmail.getEventTitle());
            model.put("StartDate",consumerEmail.getEventTimings().getStartDate());
            model.put("EndDate",consumerEmail.getEventTimings().getEndDate());
            model.put("StartTime",consumerEmail.getEventTimings().getStartTime());
            model.put("EndTime",consumerEmail.getEventTimings().getEndTime());
            model.put("venueName",consumerEmail.getVenue().getVenueName());
            model.put("houseNumber",consumerEmail.getVenue().getAddress().getHouse());
            model.put("Street",consumerEmail.getVenue().getAddress().getStreet());
            model.put("LandMark",consumerEmail.getVenue().getAddress().getLandmark());
            model.put("city",consumerEmail.getVenue().getAddress().getCity());
            model.put("State",consumerEmail.getVenue().getAddress().getState());
            model.put("Country",consumerEmail.getVenue().getAddress().getCountry());
            model.put("Pincode",consumerEmail.getVenue().getAddress().getPincode());
            model.put("totalTickerPrice",consumerEmail.getTicketPrice());
            model.put("listOfSeats",seat);
            String html= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);


            helper.setTo(consumerEmail.getEventConsumerEmailId());
            helper.setText(html,true);
            helper.setSubject("sending ticketing details");
            //  helper.setFrom(sender);
            javaMailSender.send(message);

            mailResponse.setMessage("mail send to : " + consumerEmail.getEventConsumerEmailId());
            mailResponse.setStatus(Boolean.TRUE);
        }catch (MessagingException | IOException | TemplateException e) {
            mailResponse.setMessage("Mail Sending failure : "+e.getMessage());
            mailResponse.setStatus(Boolean.FALSE);
        }

        return mailResponse;

    }



}

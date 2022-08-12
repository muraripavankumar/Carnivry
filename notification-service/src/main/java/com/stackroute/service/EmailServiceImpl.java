package com.stackroute.service;

import com.stackroute.model.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmailServiceImpl implements EmailService{

    private JavaMailSender javaMailSender;

    private Configuration configuration;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, Configuration configuration) {
        log.info("constructing EmailServiceImpl");
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Value("${spring.mail.username}") private String sender;
    @Override
    public MailResponse mailToProducer(ProducerEmail producerEmail) {
        MailResponse mailResponse=new MailResponse();
        MimeMessage message=javaMailSender.createMimeMessage();

        try{
            log.debug("inside mailToProducer() method");
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
            model.put("PinCode",producerEmail.getVenue().getAddress().getPincode());
            model.put("TotalSeats",producerEmail.getTotalSeats());
            String html= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);


            helper.setTo(producerEmail.getEventProducerEmailId());
            helper.setText(html,true);
            helper.setSubject("Sending Your new-event details");
          //  helper.setFrom(sender);
            javaMailSender.send(message);

            mailResponse.setMessage("mail send to : " + producerEmail.getEventProducerEmailId());
            mailResponse.setStatus(Boolean.TRUE);

        }catch (MessagingException | IOException | TemplateException e) {
            log.error("Exception  in EmailServiceImpl class->mailToProducer() method");
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
            log.debug("inside mailToConsumer() method");
            MimeMessageHelper helper=new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template template=configuration.getTemplate("consumerEmail-template.ftl");
            List<Seat> seat=new ArrayList<Seat>();
            seat.addAll(consumerEmail.getBoughtSeats());
            List<SeatDetails> s1=convert(seat);
            Map<String, Object> model = new HashMap<>();
            model.put("consumerName",consumerEmail.getEventConsumerName());
            model.put("eventTitle",consumerEmail.getEventTitle());
            model.put("eventDescription",consumerEmail.getEventDescription());
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
            model.put("listOfSeats",s1);
            model.put("noOfSeats",consumerEmail.getNoOfSeats());
            String html= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);


            helper.setTo(consumerEmail.getEventConsumerEmailId());
            helper.setText(html,true);
            helper.setSubject("Sending ticketing details");
            //  helper.setFrom(sender);
            javaMailSender.send(message);

            mailResponse.setMessage("mail send to : " + consumerEmail.getEventConsumerEmailId());
            mailResponse.setStatus(Boolean.TRUE);
        }catch (MessagingException | IOException | TemplateException e) {
            log.error("Exception  in EmailServiceImpl class->mailToConsumer() method");
            mailResponse.setMessage("Mail Sending failure : "+e.getMessage());
            mailResponse.setStatus(Boolean.FALSE);
        }

        return mailResponse;

    }

    public List<SeatDetails> convert(List<Seat> seat) {
        List<SeatDetails> pros = new ArrayList<>();
        for (Seat seat1: seat) {
            SeatDetails seatDetails=new SeatDetails();
            seatDetails.setSeatId(seat1.getSeatId());
            seatDetails.setSeatCategory(seat1.getSeatCategory());
            seatDetails.setSeatPrice(seat1.getSeatPrice());
            pros.add(seatDetails);
        }
        return pros;
    }



}

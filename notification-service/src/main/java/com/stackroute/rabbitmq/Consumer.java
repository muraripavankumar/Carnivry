package com.stackroute.rabbitmq;

import com.stackroute.model.ProducerEmail;
import com.stackroute.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private EmailService emailService;

    @Autowired
    public Consumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "queue_4")
    public void getDataAndAddToProducerEmail(EventDTO eventDTO){
        ProducerEmail producerEmail=new ProducerEmail();
        producerEmail.setEventProducerEmailId(eventDTO.getEventProducerEmailId());
        producerEmail.setEventProducerName(eventDTO.getEventProducerName());
        producerEmail.setEventTitle(eventDTO.getEventTitle());
        producerEmail.setEventTimings(eventDTO.getEventTimings());
        producerEmail.setVenue(eventDTO.getVenue());
        producerEmail.setTotalSeats(eventDTO.getTotalSeats());
        emailService.mailToProducer(producerEmail);
    }
}

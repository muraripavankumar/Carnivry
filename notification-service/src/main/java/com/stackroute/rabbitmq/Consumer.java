package com.stackroute.rabbitmq;

import com.stackroute.model.ConsumerEmail;
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

    @RabbitListener(queues = "management_notification_queue")
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

//    @RabbitListener(queues = "payment_notification_queue")
//    public void getDataAndAddToConsumerEmail(EventDetailsDTO eventDetailsDTO){
//        ConsumerEmail consumerEmail=new ConsumerEmail();
//        consumerEmail.setEventConsumerEmailId(eventDetailsDTO.getEventConsumerEmailId());
//        consumerEmail.setEventConsumerName(eventDetailsDTO.getEventConsumerName());
//        consumerEmail.setEventTitle(eventDetailsDTO.getEventTitle());
//        consumerEmail.setEventDescription(eventDetailsDTO.getEventDescription());
//        consumerEmail.setEventTimings(eventDetailsDTO.getEventTimings());
//        consumerEmail.setVenue(eventDetailsDTO.getVenue());
//        consumerEmail.setBoughtSeats(eventDetailsDTO.getBoughtSeats());
//        consumerEmail.setTicketPrice(eventDetailsDTO.getTicketPrice());
//        emailService.mailToConsumer(consumerEmail);
//    }
}

package com.stackroute.service;

import com.stackroute.model.ConsumerEmail;
import com.stackroute.model.MailResponse;
import com.stackroute.model.ProducerEmail;

public interface EmailService {

    MailResponse mailToProducer(ProducerEmail producerEmail);



    MailResponse mailToConsumer(ConsumerEmail consumerEmail);
}

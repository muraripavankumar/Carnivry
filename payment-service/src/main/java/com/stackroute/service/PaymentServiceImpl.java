package com.stackroute.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackroute.entity.Payment;
import com.stackroute.exception.OrderIdNotFoundException;
import com.stackroute.model.PaymentRequest;
import com.stackroute.model.PaymentSuccess;
import com.stackroute.rabbitMq.MessageProducer;
import com.stackroute.rabbitMq.NotificationServiceDTO;
import com.stackroute.rabbitMq.RegistrationServiceDTO;
import com.stackroute.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{


    private final PaymentRepository repository;
    private final MessageProducer messageProducer;

    @Autowired
    public PaymentServiceImpl(PaymentRepository repository, MessageProducer messageProducer) {
        this.repository = repository;
        this.messageProducer = messageProducer;
    }

    @Override
    public String createOrder(PaymentRequest paymentRequest) throws RazorpayException {
        log.info("Creating order for user with email id {} of amount {}",paymentRequest.getEmail(),paymentRequest.getAmount());
        int amount= Integer.parseInt(paymentRequest.getAmount());

        var client= new RazorpayClient("rzp_test_Sxqcnn9dko0BzB","566XuemLbP5nwfWClEtZDX2V");
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount*100); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "rpay_"+new Date().toString());

        Order order = client.orders.create(orderRequest);
        String orderId= order.get("id");

        Payment payment= new Payment();
        payment.setOrderId(orderId);
        payment.setEmail(paymentRequest.getEmail());
        payment.setAmount(amount);
        payment.setEventId(paymentRequest.getEventId());
        payment.setPaid(false);

        repository.save(payment);

       log.info("Order of user  with email id {} is {}",paymentRequest.getEmail(),order);
        return order.toString();
    }

    @Override
    public String savePaymentId(PaymentSuccess paymentSuccess) throws OrderIdNotFoundException {
        if(repository.findById(paymentSuccess.getOrderId()).isEmpty())
        {
            log.error("OrderId {} doesn't exists",paymentSuccess.getOrderId());
            throw new OrderIdNotFoundException();
        }
        //Saving data to payment database
        Payment payment= repository.findById(paymentSuccess.getOrderId()).get();
        payment.setPaymentId(paymentSuccess.getPaymentId());
        payment.setSignature(paymentSuccess.getSignature());
        payment.setPaid(true);
        repository.save(payment);
        log.info("PaymentId for the orderId {} successfully saved",payment.getOrderId());

        //Sending data to registration microservice
        RegistrationServiceDTO registrationServiceDTO= new RegistrationServiceDTO();
        registrationServiceDTO.setEmail(paymentSuccess.getEmail());
        registrationServiceDTO.setEventId(paymentSuccess.getEventId());
        registrationServiceDTO.setArtists(paymentSuccess.getArtists());
        registrationServiceDTO.setDescription(paymentSuccess.getDescription());
        registrationServiceDTO.setSeats(paymentSuccess.getSeats());
        registrationServiceDTO.setHost(paymentSuccess.getHost());
        registrationServiceDTO.setImage(paymentSuccess.getImage());
        registrationServiceDTO.setTimings(paymentSuccess.getTimings());
        registrationServiceDTO.setTitle(paymentSuccess.getTitle());
        registrationServiceDTO.setVenue(paymentSuccess.getVenue());
        registrationServiceDTO.setNoOfSeats(paymentSuccess.getNoOfSeats());
        messageProducer.sendMessageToRegistrationService(registrationServiceDTO);
        log.debug("Ticket details of user with email id {} sent to message producer for registration microservice"
                ,paymentSuccess.getEmail());

        //sending data to Notification microservice
        NotificationServiceDTO notificationServiceDTO= new NotificationServiceDTO();
        notificationServiceDTO.setAmount(paymentSuccess.getAmount());
        notificationServiceDTO.setSeats(paymentSuccess.getSeats());
        notificationServiceDTO.setDescription(paymentSuccess.getDescription());
        notificationServiceDTO.setEmail(paymentSuccess.getEmail());
        notificationServiceDTO.setTimings(paymentSuccess.getTimings());
        notificationServiceDTO.setEventId(paymentSuccess.getEventId());
        notificationServiceDTO.setTitle(paymentSuccess.getTitle());
        notificationServiceDTO.setUsername(paymentSuccess.getUsername());
        notificationServiceDTO.setVenue(paymentSuccess.getVenue());
        notificationServiceDTO.setNoOfSeats(paymentSuccess.getNoOfSeats());
        messageProducer.sendMessageToNotificationService(notificationServiceDTO);

        log.debug("Ticket details of user with email id {} sent to message producer for notification microservice"
                ,paymentSuccess.getEmail());

        return "Payment Success Data for orderId "+payment.getOrderId()+ " saved";
    }
}


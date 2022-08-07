package com.stackroute.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackroute.entity.Payment;
import com.stackroute.exception.OrderIdNotFoundException;
import com.stackroute.model.PaymentRequest;
import com.stackroute.model.PaymentSuccess;
import com.stackroute.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{


    PaymentRepository repository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
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
        payment.setEmail(payment.getEmail());
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
        Payment payment= repository.findById(paymentSuccess.getOrderId()).get();
        payment.setPaymentId(paymentSuccess.getPaymentId());
        payment.setSignature(paymentSuccess.getSignature());
        payment.setPaid(true);
        repository.save(payment);

        log.info("PaymentId for the orderId {} successfully saved",payment.getOrderId());
        return "Payment Success Data for orderId "+payment.getOrderId()+ " saved";
    }
}


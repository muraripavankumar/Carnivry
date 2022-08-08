package com.stackroute;


import com.stackroute.entity.Payment;
import com.stackroute.exception.OrderIdNotFoundException;
import com.stackroute.model.PaymentRequest;
import com.stackroute.model.PaymentSuccess;
import com.stackroute.repository.PaymentRepository;
import com.stackroute.service.PaymentService;
import com.stackroute.service.PaymentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    PaymentRepository repository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    Payment payment;


    @BeforeEach
    public void initialize(){
        repository.deleteAll();

        payment= new Payment();
        payment.setEventId("123");
        payment.setEmail("abc@gmail.com");
        payment.setOrderId("order1");
        payment.setAmount(200);
        payment.setPaid(false);
    }

    @AfterEach
    public void clean(){
        payment= null;
    }

    @Test
    public void createOrder(){
        when(repository.save(any(Payment.class))).thenReturn(payment);

        PaymentRequest paymentRequest= new PaymentRequest();
        paymentRequest.setAmount(payment.getAmount()+"");
        paymentRequest.setEmail(payment.getEmail());
        paymentRequest.setEventId(payment.getEventId());

       try {
           String result= paymentService.createOrder(paymentRequest);

//           assert
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    @Test
    public void savePaymentIdSuccess(){
        when(repository.findById(payment.getOrderId())).thenReturn(Optional.ofNullable(payment));
        when(repository.save(any(Payment.class))).thenReturn(payment);

        PaymentSuccess paymentSuccess= new PaymentSuccess();
        paymentSuccess.setPaymentId("payment_1");
        paymentSuccess.setSignature("Signature_1");
        paymentSuccess.setOrderId(payment.getOrderId());
        try {
            assertEquals( "Payment Success Data for orderId "+payment.getOrderId()+ " saved",paymentService.savePaymentId(paymentSuccess));
        } catch (OrderIdNotFoundException e) {
            e.printStackTrace();
        }

        verify(repository,times(2)).findById(payment.getOrderId());
        verify(repository,times(1)).save(payment);
    }

    @Test
    public void savePaymentIdFailure(){
        when(repository.findById(payment.getOrderId())).thenReturn(Optional.empty());


        PaymentSuccess paymentSuccess= new PaymentSuccess();
        paymentSuccess.setPaymentId("payment_1");
        paymentSuccess.setSignature("Signature_1");
        paymentSuccess.setOrderId(payment.getOrderId());

        assertThrows(OrderIdNotFoundException.class,()->paymentService.savePaymentId(paymentSuccess));

        verify(repository,times(1)).findById(payment.getOrderId());

    }
}

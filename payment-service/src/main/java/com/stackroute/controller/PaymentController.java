package com.stackroute.controller;


import com.stackroute.exception.OrderIdNotFoundException;
import com.stackroute.model.PaymentRequest;
import com.stackroute.model.PaymentSuccess;
import com.stackroute.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class PaymentController {


    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create_order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentRequest paymentRequest){
        try {
            return new ResponseEntity<>(paymentService.createOrder(paymentRequest), HttpStatus.CREATED);
        }catch (Exception e)
        {
            log.error("{}",e.getMessage());
            return new ResponseEntity<>("Internal server error, Will fix this soon", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/payment_success")
    public ResponseEntity<?> savePaymentId(@RequestBody PaymentSuccess paymentSuccess){
        try {
            String response= paymentService.savePaymentId(paymentSuccess);
            log.debug("{}",response);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (OrderIdNotFoundException o)
        {
            log.error("OrderId {} not found",paymentSuccess.getOrderId());
            return new ResponseEntity<>("OrderId"+paymentSuccess.getOrderId()+" doesn't exists",HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            log.error("{}",e.getMessage());
            return new ResponseEntity<>("Internal server error, Will fix this soon", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


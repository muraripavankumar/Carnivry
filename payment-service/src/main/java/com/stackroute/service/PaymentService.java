package com.stackroute.service;

import com.razorpay.RazorpayException;
import com.stackroute.exception.OrderIdNotFoundException;
import com.stackroute.model.PaymentRequest;
import com.stackroute.model.PaymentSuccess;

public interface PaymentService {
    String createOrder(PaymentRequest paymentRequest) throws RazorpayException;

    String savePaymentId(PaymentSuccess paymentSuccess) throws OrderIdNotFoundException;
}


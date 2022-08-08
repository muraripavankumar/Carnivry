package com.stackroute;

import com.stackroute.entity.Payment;
import com.stackroute.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    PaymentRepository repository;


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
    public void savePaymentOrder()
    {
        repository.save(payment);
        Payment result= repository.findById(payment.getOrderId()).get();
        assertEquals(payment.getEmail(),result.getEmail());
        assertEquals(1,repository.findAll().size());
    }

}

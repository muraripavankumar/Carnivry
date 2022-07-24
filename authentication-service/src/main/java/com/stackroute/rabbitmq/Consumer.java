package com.stackroute.rabbitmq;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.model.User;
import com.stackroute.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private UserService userService;

    @RabbitListener(queues = "usersoulmate_queue")
    public void getDataAndAddToDatabase(UserDTO userDTO) throws UserAlreadyExistsException {
        User user=new User();
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(userDTO.getPassword());
        userService.saveUser(user);
    }
}

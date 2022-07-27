package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.User;
import com.stackroute.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        log.info("constructing UserServiceImpl");
        this.userRepository = userRepository;
    }

    //    method to save new user datails
    @Override
    public User saveUser(User user) {

            log.debug("User with email id {} saved",user.getEmail());
            return userRepository.save(user);

    }

    //    method to authenticate user
    @Override
    public User authenticateUser(String email, String password) throws UserNotFoundException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) { //authentication is ok
            log.debug("inside authenticateUser() method");
            return user;
        } else { // authentication failed
            log.error("Exception  in UserServiceImpl class->authenticateUser() method");
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //    method to fetch user details if required
    @Override
    public User getUser(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            log.debug("inside getUser() method");
            return user;
        } else {
            log.error("Exception  in UserServiceImpl class->getUser() method");
            throw new UserNotFoundException();
        }
    }

    //    method to reset passaord if user forgot password
    @Override
    public User forgotPassword(User user) throws UserNotFoundException {
        if (userRepository.findById(user.getEmail()).isEmpty()){
            log.error("Exception  in UserServiceImpl class->resetPassword() method");
            throw new UserNotFoundException();
        }
        else{
            log.debug("inside resetPassword() method");
            return userRepository.save(user);
        }
    }

    //    method to check whether the user details(emailid) is valid when user forgot password
    @Override
    public User emailLink(String email) throws UserNotFoundException {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(email));
        if (!userOptional.isPresent()) {
            log.error("Exception  in UserServiceImpl class->forgotPassword() method");
            throw  new UserNotFoundException();
        }
        log.debug("inside forgotPassword() method");
        User user = userOptional.get();
        return user;
    }

}

package com.stackroute.service;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;
import com.stackroute.rabbitMQ.AuthenticationUserDTO;
import com.stackroute.rabbitMQ.MessageProducer;
import com.stackroute.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final MessageProducer messageProducer;

   @Autowired
   public UserServiceImpl(UserRepository userRepository, EmailSenderService emailSenderService, MessageProducer messageProducer) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.messageProducer = messageProducer;
    }

    private static  final int EXPIRATION_TIME = 10;

    @Override
    public CarnivryUser registerUser(UserRegModel userModel) throws UserAlreadyExistsException {
        if(userRepository.findById(userModel.getEmail().toLowerCase()).isPresent())
            throw new UserAlreadyExistsException();
        else {
            CarnivryUser carnivryUser = new CarnivryUser();
            carnivryUser.setEmail(userModel.getEmail().toLowerCase());
            carnivryUser.setName(userModel.getName());
            carnivryUser.setVerified(false);

            AuthenticationUserDTO authenticationUserDTO=
                    new AuthenticationUserDTO(userModel.getEmail().toLowerCase(), userModel.getPassword());

            messageProducer.sendMessageToAuthenticationService(authenticationUserDTO);

            return userRepository.save(carnivryUser);
        }
    }

    @Override
    public CarnivryUser registerSocialUser(UserRegModel userRegModel) throws UserAlreadyExistsException {
        if(userRepository.existsById(userRegModel.getEmail()))
            throw new UserAlreadyExistsException();

        CarnivryUser carnivryUser = new CarnivryUser();
        carnivryUser.setEmail(userRegModel.getEmail());
        carnivryUser.setName(userRegModel.getName());

        carnivryUser.setVerified(true);

        CarnivryUser result= userRepository.save(carnivryUser);
        return result;

    }

    @Override
    public void addLikedGenres(AddGenre addGenre) throws UserNotFoundException {
//        System.out.println(addGenre.getEmail());
        if (userRepository.findById(addGenre.getEmail()).isEmpty())
            throw new UserNotFoundException();

        CarnivryUser carnivryUser= userRepository.findById(addGenre.getEmail()).get();

            Preferences preferences= carnivryUser.getPreferences();
            if(preferences==null)
                preferences= new Preferences();
            Set<Genre> genreList= preferences.getLikedGenres();
            if(genreList==null)
                genreList= new HashSet<>();
            for (String genre:addGenre.getGenres()){
                genreList.add(Genre.valueOf(genre.toUpperCase()));
            }
            preferences.setLikedGenres(genreList);
            carnivryUser.setPreferences(preferences);
            userRepository.save(carnivryUser);
    }

    @Override
    public List<String> getAllGenres() {
        List<String> allGenres= new ArrayList<>();
        List<Genre> genres= List.of(Genre.values());

        for(Genre genre:genres) {allGenres.add(genre.toString());}

        return allGenres;
    }

    @Override
    public void saveVerificationTokenForUser(String token, CarnivryUser carnivryUser) throws UserNotFoundException {

        if(userRepository.findById(carnivryUser.getEmail()).isEmpty())
            throw new UserNotFoundException();
        carnivryUser.setEmailVerificationToken(token);
        carnivryUser.setEvtExpTime(calculateExpirationDate(EXPIRATION_TIME));

        userRepository.save(carnivryUser);
    }

    @Override
    public String validateVerificationToken(String token, String email) {

        CarnivryUser carnivryUser= userRepository.findByEmailVerificationToken(token);
        if(carnivryUser==null)
            return "invalid token";
        else {
            if(!(carnivryUser.getEmail().equals(email)))
                return "invalid token";
            Calendar cal = Calendar.getInstance();
            if ((carnivryUser.getEvtExpTime().getTime() - cal.getTime().getTime()) <= 0) {
                carnivryUser.setEmailVerificationToken("");
                carnivryUser.setEvtExpTime(null);
                userRepository.save(carnivryUser);
                return "token expired";
            }
            else{
                carnivryUser.setVerified(true);
                userRepository.save(carnivryUser);
                return "valid token";
            }
        }
    }


    @Override
    public boolean isUserVerified(String email) throws UserNotFoundException {
        if(userRepository.findById(email).isEmpty())
            throw new UserNotFoundException();
        CarnivryUser carnivryUser= userRepository.findById(email).get();
        return carnivryUser.getVerified();
    }

    @Override
    public void regenerateEmailVerificationToken(String email, String applicationUrl) throws UserNotFoundException {

        if (userRepository.findById(email).isEmpty())
            throw new UserNotFoundException();
        CarnivryUser carnivryUser= userRepository.findById(email).get();
        String token=  UUID.randomUUID().toString();
        carnivryUser.setEmailVerificationToken(token);
        carnivryUser.setEvtExpTime(calculateExpirationDate(EXPIRATION_TIME));
        userRepository.save(carnivryUser);

        String url =
                applicationUrl
                        + "/Carnivry/verifyRegistration?token="
                        + token
                        + "&email="
                        + email;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);

        EmailRequest emailRequest= new EmailRequest();
        emailRequest.setSubject("Carnivry Account- Email Verification");
        emailRequest.setTo(carnivryUser.getEmail());

        Map<String, Object> model = new HashMap<>();
        model.put("title","Hello User,verify your email");
        model.put("text", "Please click on the button below to get your Carnivry Account email verified");
        model.put("url", url);
        model.put("button","Verify");

        emailSenderService.sendEmailWithAttachment(emailRequest,model);
    }

    @Override
    public boolean isUserPresent(String email) {
        return userRepository.findById(email).isPresent();
    }



    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}


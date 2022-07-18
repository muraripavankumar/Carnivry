package com.stackroute.service;

import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.AddGenre;
import com.stackroute.model.Genre;
import com.stackroute.model.Preferences;
import com.stackroute.model.UserRegModel;
import com.stackroute.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

   @Autowired
    EmailSenderService emailSenderService;

    private static  final int EXPIRATION_TIME = 10;

    @Override
    public CarnivryUser registerUser(UserRegModel userModel) throws UserAlreadyExistsException {
        if(userRepository.findByEmail(userModel.getEmail())!=null)
            throw new UserAlreadyExistsException();
        else {
            CarnivryUser carnivryUser = new CarnivryUser();
            carnivryUser.setEmail(userModel.getEmail());
            carnivryUser.setName(userModel.getName());
            carnivryUser.setId( UUID.randomUUID().toString());
            carnivryUser.setVerified(false);

            return userRepository.save(carnivryUser);
        }
    }

    @Override
    public CarnivryUser registerSocialUser(UserRegModel userRegModel) throws UserAlreadyExistsException {
        if(userRepository.findByEmail(userRegModel.getEmail())!=null)
            throw new UserAlreadyExistsException();

        CarnivryUser carnivryUser = new CarnivryUser();
        carnivryUser.setEmail(userRegModel.getEmail());
        carnivryUser.setName(userRegModel.getName());
        carnivryUser.setId( UUID.randomUUID().toString());
        carnivryUser.setVerified(true);

        return userRepository.save(carnivryUser);

    }

    @Override
    public void addLikedGenres(AddGenre addGenre) throws UserNotFoundException {
        System.out.println(addGenre.getEmail());
        CarnivryUser carnivryUser= userRepository.findByEmail(addGenre.getEmail());

        if(carnivryUser!=null)
        {
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
        else
            throw new UserNotFoundException();


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

        if(userRepository.findById(carnivryUser.getId()).isEmpty())
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
    public boolean isUserVerified(String email) {
        CarnivryUser carnivryUser= userRepository.findByEmail(email);
        return carnivryUser.getVerified();
    }

    @Override
    public void regenerateEmailVerificationToken(String email, String applicationUrl) {
        CarnivryUser carnivryUser= userRepository.findByEmail(email);
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

        emailSenderService.sendSimpleEmail(email,
                "Click the link to verify your account: "+url,
                "Email verification");
    }

    @Override
    public boolean isUserPresent(String email) {
        return userRepository.findByEmail(email) != null;
    }



    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}

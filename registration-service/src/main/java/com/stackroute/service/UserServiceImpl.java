package com.stackroute.service;

//import com.stackroute.config.TwilioConfig;
import com.stackroute.entity.CarnivryUser;
import com.stackroute.exception.UserAlreadyExistsException;
import com.stackroute.exception.UserNotFoundException;
import com.stackroute.model.*;
import com.stackroute.rabbitMQ.AuthenticationUserDTO;
import com.stackroute.rabbitMQ.MessageProducer;
import com.stackroute.repository.UserRepository;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final MessageProducer messageProducer;
//    private final TwilioConfig twilioConfig;
    private final SmsSenderService smsSenderService;

   @Autowired
   public UserServiceImpl(UserRepository userRepository, EmailSenderService emailSenderService, MessageProducer messageProducer, SmsSenderService smsSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.messageProducer = messageProducer;

       this.smsSenderService = smsSenderService;
   }

    private static  final int EXPIRATION_TIME = 10;

    @Override
    public CarnivryUser registerUser(UserRegModel userModel) throws UserAlreadyExistsException {
        if(userRepository.findById(userModel.getEmail().toLowerCase()).isPresent())
        {
            log.error("Couldn't register user since email id {} already exists",userModel.getEmail());
            throw new UserAlreadyExistsException();
        }
        else {
            CarnivryUser carnivryUser = new CarnivryUser();
            carnivryUser.setEmail(userModel.getEmail().toLowerCase());
            carnivryUser.setName(userModel.getName());
            carnivryUser.setVerified(false);

            AuthenticationUserDTO authenticationUserDTO=
                    new AuthenticationUserDTO(userModel.getEmail().toLowerCase(), userModel.getPassword());

            messageProducer.sendMessageToAuthenticationService(authenticationUserDTO);

            log.info("New Carnivry Account created with email id {}",userModel.getEmail());

            return userRepository.save(carnivryUser);
        }
    }

    @Override
    public CarnivryUser registerSocialUser(UserRegModel userRegModel) throws UserAlreadyExistsException {
        if(userRepository.existsById(userRegModel.getEmail()))
        {
            log.error("Couldn't register user since email id {} already exists",userRegModel.getEmail());
            throw new UserAlreadyExistsException();
        }

        CarnivryUser carnivryUser = new CarnivryUser();
        carnivryUser.setEmail(userRegModel.getEmail());
        carnivryUser.setName(userRegModel.getName());

        carnivryUser.setVerified(true);

        CarnivryUser result= userRepository.save(carnivryUser);
        log.info("New Social Carnivry Account created with email id {}",userRegModel.getEmail());
        return result;

    }

    @Override
    public void addLikedGenres(AddGenre addGenre) throws UserNotFoundException {
//        System.out.println(addGenre.getEmail());
        if (userRepository.findById(addGenre.getEmail()).isEmpty())
        {
            log.error("Couldn't add user liked genres since user with email id {} is not present",addGenre.getEmail());
            throw new UserNotFoundException();
        }

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

            log.info("Liked Genres added to user with email id {}",addGenre.getEmail());
    }

    @Override
    public List<String> getAllGenres() {
        List<String> allGenres= new ArrayList<>();
        List<Genre> genres= List.of(Genre.values());

        for(Genre genre:genres) {allGenres.add(genre.toString());}

        log.debug("Returning all Carnivry event genres");
        return allGenres;
    }

    @Override
    public void saveProfilePic(AddProfilePic addProfilePic) throws UserNotFoundException {

        if(userRepository.findById(addProfilePic.getEmail()).isEmpty())
        {
            log.error("Couldn't save profile picture since no user with email id {} exists in " +
                    "Carnivry Registration database",addProfilePic.getEmail());
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(addProfilePic.getEmail()).get();
        carnivryUser.setProfilePic(addProfilePic.getProfilePic());
        userRepository.save(carnivryUser);
    }

    @Override
    public String getUsername(String email) throws UserNotFoundException {
        if(userRepository.findById(email).isEmpty())
        {
            log.error("Couldn't fetch username since user with email id {} doesn't exists",email);
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(email).get();
        return carnivryUser.getName();
    }

    @Override
    public void saveVerificationTokenForUser(String token, CarnivryUser carnivryUser) throws UserNotFoundException {

        if(userRepository.findById(carnivryUser.getEmail()).isEmpty())
        {
            log.error("Couldn't save first email verification token since no user with email id {} exists in " +
                    "Carnivry Registration database",carnivryUser.getEmail());
            throw new UserNotFoundException();
        }
        carnivryUser.setEmailVerificationToken(token);
        carnivryUser.setEvtExpTime(calculateExpirationDate(EXPIRATION_TIME));

        userRepository.save(carnivryUser);
        log.info("First Email Verification token saved for user with email id {}",carnivryUser.getEmail());
    }

    @Override
    public String validateVerificationToken(String token, String email) {

        CarnivryUser carnivryUser= userRepository.findByEmailVerificationToken(token);
        if(carnivryUser==null)
        {
            log.error("Email verification token {} is invalid for user with email id {}",token,email);
            return "invalid token";
        }
        else {
            if(!(carnivryUser.getEmail().equals(email)))
            {
                log.error("Email verification token {} is invalid for user with email id {}",token,email);
                return "invalid token";
            }
            Calendar cal = Calendar.getInstance();
            if ((carnivryUser.getEvtExpTime().getTime() - cal.getTime().getTime()) <= 0) {
                carnivryUser.setEmailVerificationToken("");
                carnivryUser.setEvtExpTime(null);
                userRepository.save(carnivryUser);
                log.error("Email verification token {} expired for user with email id {}",token,email);
                return "token expired";
            }
            else{
                carnivryUser.setVerified(true);
                userRepository.save(carnivryUser);
                log.info("User with email id {} is verified",email);
                return "valid token";
            }
        }
    }


    @Override
    public boolean isUserVerified(String email) throws UserNotFoundException {
        if(userRepository.findById(email).isEmpty())
        {
            log.error("User with email id {} is not present, so verification status couldn't be checked", email);
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(email).get();
        log.debug("Verification status of user with email id {} is fetched",email);
        return carnivryUser.getVerified();
    }

    @Override
    public void regenerateEmailVerificationToken(String email, String applicationUrl) throws UserNotFoundException {

        if (userRepository.findById(email).isEmpty())
        {
            log.error("Email verification token couldn't be send as user with email id {} doesn't exists",email);
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(email).get();
        String token=  UUID.randomUUID().toString();
        carnivryUser.setEmailVerificationToken(token);
        carnivryUser.setEvtExpTime(calculateExpirationDate(EXPIRATION_TIME));
        userRepository.save(carnivryUser);

        String url =
                applicationUrl
                        + "/api/v1/verifyRegistration?token="
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
        log.info("Email verification token for user with email id {} is generated",email);
    }

    @Override
    public boolean isUserPresent(String email) {
        log.debug("Email id {} is searched in the Carnivry User database",email);
        return userRepository.findById(email).isPresent();
    }


//    public void savePhoneNumber(String email, String phone) {
//
//        CarnivryUser carnivryUser= userRepository.findById(email).get();
//        carnivryUser.setPhone(phone);
//        userRepository.save(carnivryUser);
//    }

    @Override
    public boolean sendOTPForPhoneNoVerification(PhoneNoValidationRequestDto phoneNoValidationRequestDto) throws UserNotFoundException {

        if(userRepository.findById(phoneNoValidationRequestDto.getEmail()).isEmpty()){
            log.error("Cannot send otp to phone number of unregistered user with email id {}"
                    ,phoneNoValidationRequestDto.getEmail());
            throw new UserNotFoundException();
        }
        try {
            CarnivryUser carnivryUser= userRepository.findById(phoneNoValidationRequestDto.getEmail()).get();
//            PhoneNumber to = new PhoneNumber(phoneNoValidationRequestDto.getPhoneNumber());
//            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOTP();
            String otpMessage = "Dear User , Your OTP is ##" + otp + "##. Use this OTP to complete your phone number verification.";

//            log.info("SID: {}, Token: {}",twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
////            Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
//            Message message = Message.creator( to,from, otpMessage).create();
//            System.out.println(message.getSid());

            smsSenderService.sendSms(otpMessage,phoneNoValidationRequestDto.getPhoneNumber());

            carnivryUser.setPhoneNoVerificationOTP(otp);
            carnivryUser.setPvoExpTime(calculateExpirationDate(EXPIRATION_TIME));
            userRepository.save(carnivryUser);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Couldn't send otp to phone number {}",phoneNoValidationRequestDto.getPhoneNumber());
            return false;
        }
        return true;
    }

    @Override
    public String validatePhoneVerificationOTP(PhoneNoValidationRequestDto phoneNoValidationRequestDto) throws UserNotFoundException {
        if(userRepository.findById(phoneNoValidationRequestDto.getEmail()).isEmpty()){
            log.error("Cannot validate phone number verification otp since no user with email id {} exists"
                    ,phoneNoValidationRequestDto.getEmail());
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(phoneNoValidationRequestDto.getEmail()).get();
        if(carnivryUser.getPhoneNoVerificationOTP().equals(phoneNoValidationRequestDto.getOneTimePassword())){
            Calendar cal = Calendar.getInstance();
            if ((carnivryUser.getPvoExpTime().getTime() - cal.getTime().getTime()) <= 0){
                log.debug("Phone Number verification otp expired for user with email id {}",carnivryUser.getEmail());
                carnivryUser.setPhoneNoVerificationOTP(null);
                carnivryUser.setPvoExpTime(null);
                userRepository.save(carnivryUser);
                return "Otp expired";
            }
            else {
                log.info("Phone number verified successfully for user with email id {}",carnivryUser.getEmail());
                carnivryUser.setPhoneNoVerificationOTP(null);
                carnivryUser.setPvoExpTime(null);
                carnivryUser.setPhone(phoneNoValidationRequestDto.getPhoneNumber());
                userRepository.save(carnivryUser);
                return "Valid otp";
            }
        }
        else {
            log.debug("Invalid otp for phone number of user with email id {}",carnivryUser.getEmail());
            return "Invalid otp";
        }

    }

    @Override
    public void saveDOB(AddDOB addDOB) throws UserNotFoundException {
        if (userRepository.findById(addDOB.getEmail()).isEmpty())
        {
            log.debug("User with email id {} not found",addDOB.getEmail());
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(addDOB.getEmail()).get();
        carnivryUser.setDob(addDOB.getDob());
        userRepository.save(carnivryUser);
    }

    @Override
    public void saveAddress(AddAddress addAddress) throws UserNotFoundException {
        if (userRepository.findById(addAddress.getEmail()).isEmpty())
        {
            log.debug("User with email id {} not found",addAddress.getEmail());
            throw new UserNotFoundException();
        }
        CarnivryUser carnivryUser= userRepository.findById(addAddress.getEmail()).get();
        carnivryUser.setAddress(addAddress.getAddress());
        userRepository.save(carnivryUser);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        log.debug("Expiration Time is Generated");
        return new Date(calendar.getTime().getTime());
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}


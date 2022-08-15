package com.stackroute.Services;

import com.stackroute.Respository.EventsRepo;
import com.stackroute.entity.Events;
import com.stackroute.entity.User;
import com.stackroute.exception.EventAlreadyExistException;
import com.stackroute.exception.EventNotFoundException;
import com.stackroute.exception.UserAlreadyExistException;
import com.stackroute.exception.UserNotfoundException;
import com.stackroute.Respository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    UserRepo userRepo;
    EventsRepo eventsRepo;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    @Autowired
    public UserService(UserRepo userRepo, EventsRepo eventsRepo) {
        this.userRepo = userRepo;
        this.eventsRepo = eventsRepo;
        date.setDate(4);
    }

    Logger logger = Logger.getLogger(UserService.class.getName());

    //add new user
    public User addUser(User user) throws UserAlreadyExistException {

        if(userRepo.findById(user.getEmailId()).isPresent()){
            throw new UserAlreadyExistException();
        }
        else {
            //save the user
            if(user.getWishlist()==null){
                logger.info("wishlist is null when the user is added");
                user.setWishlist(new ArrayList<String>());
            }
            userRepo.save(user);

            //get all the events
            List<Events> eventsList = eventsRepo.findAll();

            //iterating the event list in order to connect with user have same genre
            ListIterator<Events> iterator = eventsList.listIterator();
            while (iterator.hasNext()) {
                Events events = iterator.next();
                for (int i = 0; i < user.getLikedGenre().size(); i++) {
                    if (events.getGenre().contains(user.getLikedGenre().get(i))) {
                        userRepo.connectWithEvents(user.getName(), events.getTitle());
                        logger.info("Users matching the event genre: " + events.getTitle());
                        break;
                    }
                }
            }

            logger.info("User added successfully");
        }
        return user;
    }

    //update user wishlist
    public String updateUserWishlist(String emailId, String eventId) throws EventAlreadyExistException {
        String response="";

        if(emailId==null){
            response="Please login before adding wishlist";
        }
        else {

            //adding event in wishlist
            User user = userRepo.findByEmailId(emailId);
            String event = eventsRepo.findById(eventId).get().getEventId();
            List<String> userWishlist = user.getWishlist();
//        System.out.println("Wishlist: "+userWishlist);
            logger.info("Wishlist: " + userWishlist);
            if(userWishlist!=null) {
                logger.info("'wishlist is not null");
                if (userWishlist.contains(event)) {
                    response = "You have already added this event in wishlist";
//            System.out.println("You have already added this event in wishlist");
                    logger.info("You have already added this event in wishlist");
                    throw new EventAlreadyExistException();
                } else {
                    userWishlist.add(event);
                    user.setWishlist(userWishlist);
//            System.out.println("Updated wishlist: " + user.getWishlist());
                    logger.info("Updated wishlist: " + user.getWishlist());
                    userRepo.save(user);
                    response = "Event saved in wishlist";
                }
            }

        }
        return response;
    }

    //retrieving all users
    public List<User> getAllUsers() throws UserNotfoundException{
        List<User> userList=userRepo.findAll();
        if(userList.isEmpty()){
            throw new UserNotfoundException();
        }
        else {
            logger.info("All Users: " + userList.size());
        }
        return userList;
    }

    //retrieve events for user suggestion
    public List<Events> getSuggestedEvents(String emailId) throws EventNotFoundException {
        List<String> suggestionList= new ArrayList<>();
        List<Events> suggestionEventsList= new ArrayList<>();
        ListIterator<Events> iterator;

        //retrieving the events of that city
        List<Events> cityEvents = new ArrayList<>();
        List<Events> allEvents = eventsRepo.findAll();
        logger.info("All Events: "+ allEvents.size());

        if(emailId.equals(" ")){
            logger.info("==============Email id id blank====================");
            int temp=0;

            List<Integer> likes= new ArrayList<>();
            for(int i=0; i<allEvents.size(); i++){
                likes.add(allEvents.get(i).getLikes());
            }

            for(int i=0; i<likes.size(); i++){
                for(int j=i+1; j<likes.size(); j++) {
                    if (likes.get(i) < likes.get(j)) {
                        temp = likes.get(i);
                        likes.set(i, likes.get(j));
                        likes.set(j, temp);

                        temp = allEvents.get(i).getLikes();
                        allEvents.get(i).setLikes(allEvents.get(j).getLikes());
                        allEvents.get(j).setLikes(temp);
                    }
                }
            }
            suggestionEventsList=allEvents;
        }

        else {
            logger.info("=============email id not blank=====================");
            User user= userRepo.findByEmailId(emailId);
            String city= user.getCity();

            iterator = allEvents.listIterator();
            while (iterator.hasNext()) {
                Events events = iterator.next();
                if (events.getCity().equals(city)) {
                    cityEvents.add(events);
                }
            }
//        System.out.println("User city events: "+ cityEvents.size());
            logger.info("User city events: " + cityEvents.size());


            //filtering the list by end date
            List<Events> filteredListByDate = new ArrayList<>();
            iterator = cityEvents.listIterator();
            while (iterator.hasNext()) {
                Events events = iterator.next();
                if (events.getEndDate().compareTo(date) >= 0) {
                    filteredListByDate.add(events);
                }
            }
//        System.out.println("Filtered list by date: "+filteredListByDate.size());
            logger.info("Filtered list by date: " + filteredListByDate.size());

            //filtering the list by genre
            List<Events> filterByGenre = new ArrayList<>();
            iterator = filteredListByDate.listIterator();
            while (iterator.hasNext()) {
                Events events = iterator.next();
                for (int i = 0; i < user.getLikedGenre().size(); i++) {
                    if (events.getGenre().contains(user.getLikedGenre().get(i))) {
                        filterByGenre.add(events);
                        break;
                    }
                }
            }
//        System.out.println("Filter by genre: "+filterByGenre.size());
            logger.info("Filter by genre: " + filterByGenre.size());

            //list of wishlist of user
            List<Events> userWishlist = new ArrayList<>();
            List<String> eventId = user.getWishlist();
            if(eventId==null){
                logger.info("EventId is fetched null");
            }
            logger.info("EventId size : "+eventId);
            if(eventId!=null){


            for (int i = 1; i < eventId.size(); i++) {
                Events events = eventsRepo.findById(eventId.get(i)).get();
                if (events.getEndDate().compareTo(date) >= 0) {
                    userWishlist.add(events);
                }
            }}

            //filter events in descending order of likes
            List<Events> filterByLikes = filteredListByDate;
            List<Integer> likes = new ArrayList<>();
            for (int i = 0; i < filterByLikes.size(); i++) {
                likes.add(filterByLikes.get(i).getLikes());
            }
//        System.out.println("Likes list: "+likes);
            logger.info("Likes list: " + likes);
            int temp = 0;
            for (int i = 0; i < likes.size(); i++) {
                for (int j = i + 1; j < likes.size(); j++) {
                    if (likes.get(i) < likes.get(j)) {
                        temp = likes.get(i);
                        likes.set(i, likes.get(j));
                        likes.set(j, temp);

                        temp = filterByLikes.get(i).getLikes();
                        filterByLikes.get(i).setLikes(filterByLikes.get(j).getLikes());
                        filterByLikes.get(j).setLikes(temp);
                    }
                }
            }

//        suggestionList.addAll(filterByLikes);
//        suggestionList.addAll(filterByGenre);
//        suggestionList.addAll(userWishlist);

            for (int i = 0; i < filterByLikes.size(); i++) {
                if (!suggestionList.contains(filterByLikes.get(i).getEventId())) {
                    suggestionList.add(filterByLikes.get(i).getEventId());
                }
            }
            for (int i = 0; i < filterByGenre.size(); i++) {
                if (!suggestionList.contains(filterByGenre.get(i).getEventId())) {
                    suggestionList.add(filterByGenre.get(i).getEventId());
                }
            }
            for (int i = 0; i < filteredListByDate.size(); i++) {
                if (!suggestionList.contains(filteredListByDate.get(i).getEventId())) {
                    suggestionList.add(filteredListByDate.get(i).getEventId());
                }
            }

            for (int i = 0; i < suggestionList.size(); i++) {
                suggestionEventsList.add(eventsRepo.findById(suggestionList.get(i)).get());
            }

            if (suggestionEventsList.isEmpty()) {
                List<Events> likesFilter = allEvents;
                List<Integer> likeLlist = new ArrayList<>();
                for (int i = 0; i < likesFilter.size(); i++) {
                    likeLlist.add(likesFilter.get(i).getLikes());
                }
//        System.out.println("Likes list: "+likes);
                logger.info("Likes list: " + likeLlist);
                int temp1 = 0;
                for (int i = 0; i < likeLlist.size(); i++) {
                    for (int j = i + 1; j < likeLlist.size(); j++) {
                        if (likeLlist.get(i) < likeLlist.get(j)) {
                            temp1 = likeLlist.get(i);
                            likeLlist.set(i, likeLlist.get(j));
                            likeLlist.set(j, temp);

                            temp1 = likesFilter.get(i).getLikes();
                            likesFilter.get(i).setLikes(likesFilter.get(j).getLikes());
                            likesFilter.get(j).setLikes(temp);
                        }
                    }
                }
                suggestionEventsList=likesFilter;
            }
                logger.info("Suggestion list: " + suggestionList.size());
                logger.info("Suggestion Event List: " + suggestionEventsList.size());

        }

        return suggestionEventsList;
    }


    //retrieve events for user suggestion
    public List<Events> getSuggestedEventsForLogout() throws EventNotFoundException {
        List<Events> suggestionEventsList= new ArrayList<>();

        //retrieving the events of that city
        List<Events> allEvents = eventsRepo.findAll();
        logger.info("All Events: "+ allEvents.size());
            logger.info("==============Email id id blank====================");
            int temp = 0;

            List<Integer> likes = new ArrayList<>();
            for (int i = 0; i < allEvents.size(); i++) {
                likes.add(allEvents.get(i).getLikes());
            }

            for (int i = 0; i < likes.size(); i++) {
                for (int j = i + 1; j < likes.size(); j++) {
                    if (likes.get(i) < likes.get(j)) {
                        temp = likes.get(i);
                        likes.set(i, likes.get(j));
                        likes.set(j, temp);

                        temp = allEvents.get(i).getLikes();
                        allEvents.get(i).setLikes(allEvents.get(j).getLikes());
                        allEvents.get(j).setLikes(temp);
                    }
                }
            }
            suggestionEventsList = allEvents;
            logger.info("User log out suggestions: "+suggestionEventsList.size());

        return suggestionEventsList;
    }

    //retrieve events for user suggestion by city
    public List<Events> getSuggestedEventsByCity(String city) throws EventNotFoundException{
        List<Events> suggestionList= new ArrayList<>();
        List<Events> allEvents = eventsRepo.findAll();

        ListIterator<Events> iterator= allEvents.listIterator();
        while (iterator.hasNext()){
            Events events= iterator.next();
            if(events.getCity().equalsIgnoreCase(city)){
                suggestionList.add(events);
            }
        }
        logger.info("Suggestion by city: "+suggestionList.size());
        return suggestionList;
    }

    //retrieve upcoming events
    public List<Events> getUpcomingEvents() throws EventNotFoundException{

        logger.info("Date: "+date);

        List<Events> eventsList= new ArrayList<>();

        List<Events> allEvents= eventsRepo.findAll();

        ListIterator<Events> iterator= allEvents.listIterator();
        while (iterator.hasNext()){
            Events events= iterator.next();
            if(events.getStartDate().compareTo(date)>0){
                eventsList.add(events);
            }
        }
        if(eventsList.isEmpty()){
            throw new EventNotFoundException();
        }
        else{
            logger.info("Upcoming events: "+eventsList.size());
        }
        return eventsList;
    }

}
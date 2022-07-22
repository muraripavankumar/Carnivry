package com.example.SuggestionService.Services;

import com.example.SuggestionService.Respository.EventsRepo;
import com.example.SuggestionService.Respository.UserRepo;
//import com.example.SuggestionService.entity.Address;
import com.example.SuggestionService.entity.Events;
import com.example.SuggestionService.entity.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    EventsRepo eventsRepo;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    //add new user
    public User addUser(User user){
        //save the user
        User user1= userRepo.save(user);

        //get all the events
        List<Events> eventsList= eventsRepo.findAll();

        //iterating the event list in order to connect with user have same genre
        ListIterator<Events> iterator= eventsList.listIterator();
        while (iterator.hasNext()){
            Events events= iterator.next();
            for(int i=0; i<user.getLikedGenre().size(); i++) {
                if (events.getGenre().contains(user.getLikedGenre().get(i))) {
                    userRepo.connectWithEvents(user.getName(),events.getTitle());
                    System.out.println("Users matching the event genre: " + events.getTitle());
                    break;
                }
            }
        }

        System.out.println("User added successfully");
        return user1;
    }

    //update user wishlist
    public String updateUserWishlist(String emailId, String eventId){
        String response="";

        //adding event in wishlist
        User user= userRepo.findByEmailId(emailId);
        String event= eventsRepo.findById(eventId).get().getEventId();
        List<String> userWishlist= user.getWishlist();
        System.out.println("Wishlist: "+userWishlist);
        if(userWishlist.contains(event)){
            response="You have already added this event in wishlist";
            System.out.println("You have already added this event in wishlist");
        }
        else {
            userWishlist.add(event);
            user.setWishlist(userWishlist);
            System.out.println("Updated wishlist: " + user.getWishlist());
            userRepo.save(user);
            response = "Event saved in wishlist";
        }
        return response;
    }

    //retrieving all users
    public List<User> getAllUsers(){
        List<User> userList=userRepo.findAll();
        System.out.println("All Users: "+userList);
        return userList;
    }

    //retrieve events for user suggestion
    public List<Events> getSuggestedEvents(String emailId){

        List<String> suggestionList= new ArrayList<>();
        List<Events> suggestionEventsList= new ArrayList<>();
        ListIterator<Events> iterator;

        User user= userRepo.findByEmailId(emailId);
        String city= user.getCity();

        //retrieving the events of that city
        List<Events> cityEvents = new ArrayList<>();
        List<Events> allEvents = eventsRepo.findAll();
        iterator=allEvents.listIterator();
        while (iterator.hasNext()){
            Events events=iterator.next();
            if(events.getCity().equals(city)){
                cityEvents.add(events);
            }
        }
        System.out.println("User city events: "+ cityEvents.size());



        //filtering the list by end date
        List<Events> filteredListByDate= new ArrayList<>();
        iterator= cityEvents.listIterator();
        while (iterator.hasNext()){
            Events events= iterator.next();
            if(events.getEndDate().compareTo(date)>=0){
                filteredListByDate.add(events);
            }
        }
        System.out.println("Filtered list by date: "+filteredListByDate.size());

        //filtering the list by genre
        List<Events> filterByGenre= new ArrayList<>();
        iterator= filteredListByDate.listIterator();
        while (iterator.hasNext()){
            Events events= iterator.next();
            for(int i=0; i<user.getLikedGenre().size(); i++){
                if(events.getGenre().contains(user.getLikedGenre().get(i))){
                    filterByGenre.add(events);
                    break;
                }
            }
        }
        System.out.println("Filter by genre: "+filterByGenre.size());

        //list of wishlist of user
        List<Events> userWishlist= new ArrayList<>();
        List<String> eventId= user.getWishlist();
        for(int i=1; i<eventId.size(); i++){
            Events events= eventsRepo.findById(eventId.get(i)).get();
            if(events.getEndDate().compareTo(date)>=0) {
                userWishlist.add(events);
            }
        }

        //filter events in descending order of likes
        List<Events> filterByLikes= filteredListByDate;
        List<Integer> likes= new ArrayList<>();
        for(int i=0; i<filterByLikes.size(); i++){
            likes.add(filterByLikes.get(i).getLikes());
        }
        System.out.println("Likes list: "+likes);
        int temp=0;
        for(int i=0; i<likes.size(); i++){
            for(int j=i+1; j<likes.size(); j++) {
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

        for(int i=0; i<filterByLikes.size(); i++){
            if(!suggestionList.contains(filterByLikes.get(i).getEventId())) {
                suggestionList.add(filterByLikes.get(i).getEventId());
            }
        }
        for(int i=0; i<filterByGenre.size(); i++){
            if(!suggestionList.contains(filterByGenre.get(i).getEventId())) {
                suggestionList.add(filterByGenre.get(i).getEventId());
            }
        }
        for(int i=0; i<filteredListByDate.size(); i++){
            if(!suggestionList.contains(filteredListByDate.get(i).getEventId())) {
                suggestionList.add(filteredListByDate.get(i).getEventId());
            }
        }

        for(int i=0; i<suggestionList.size(); i++){
            suggestionEventsList.add(eventsRepo.findById(suggestionList.get(i)).get());
        }

        System.out.println("Suggestion list: "+ suggestionList.size());
        System.out.println("Suggestion Event List: "+suggestionEventsList.size());

        return suggestionEventsList;
    }

    //retrieve upcoming events
    public List<Events> getUpcomingEvents(String emailId){
        List<Events> eventsList= new ArrayList<>();
        User user= userRepo.findByEmailId(emailId);
        String city= user.getCity();

        List<Events> allEvents= eventsRepo.findAll();

        ListIterator<Events> iterator= allEvents.listIterator();
        while (iterator.hasNext()){
            Events events= iterator.next();
            if(events.getStartDate().compareTo(date)>0){
                eventsList.add(events);
            }
        }
        System.out.println("Upcoming events: "+eventsList.size());
        return eventsList;
    }

}
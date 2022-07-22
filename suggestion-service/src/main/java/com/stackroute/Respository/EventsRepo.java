package com.example.SuggestionService.Respository;

import com.example.SuggestionService.entity.Events;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepo extends Neo4jRepository<Events,String> {


//    List<Events> findByCity(String city);


//    @Query("MATCH (e{venueName: $venueName}),(u:User) WHERE u.likedGenre = e.genre CREATE (u)-[w:LikedGenre]->(e)")
//    void userLikedGenre(String venueName);

    //connect the event with user having same likedGenre
    @Query("MATCH (u{name: $userName}),(e{title: $eventName}) CREATE (u)-[w:LikedGenre]->(e)")
    void connectWithUser(String userName, String eventName);
}
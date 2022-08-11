package com.stackroute.Respository;

import com.stackroute.entity.Events;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepo extends Neo4jRepository<Events,String> {
    //connect the event with user having same likedGenre
    @Query("MATCH (u{name: $userName}),(e{title: $eventName}) CREATE (u)-[w:LikedGenre]->(e)")
    void connectWithUser(String userName, String eventName);
}
package com.stackroute.Respository;

import com.stackroute.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends Neo4jRepository<User,String> {

    User findByEmailId(String emailId);
    //connects user with the events having same genre
    @Query("MATCH (u{name: $userName}),(e{title: $eventName}) CREATE (u)-[w:LikedGenre]->(e)")
    void connectWithEvents(String userName, String eventName);
}
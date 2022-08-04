package com.stackroute.repository;

import com.stackroute.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event,String> {
    List<Event> findByUserEmailId(String userEmail);
}

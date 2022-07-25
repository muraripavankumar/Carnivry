package com.stackroute.Repository;


import com.stackroute.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;



@Repository
@RedisHash
public interface EventRepository extends MongoRepository<Event,String> {

}


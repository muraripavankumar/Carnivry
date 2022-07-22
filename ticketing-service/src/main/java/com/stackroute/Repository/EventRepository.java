package com.stackroute.Repository;


import com.stackroute.model.Event;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
@RedisHash
public interface EventRepository extends CrudRepository<Event,String> {

}


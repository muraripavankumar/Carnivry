package com.stackroute.repository;

import com.stackroute.entity.CarnivryUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<CarnivryUser,String> {
//     CarnivryUser findByEmail(String email);
    CarnivryUser findByEmailVerificationToken(String token);
}

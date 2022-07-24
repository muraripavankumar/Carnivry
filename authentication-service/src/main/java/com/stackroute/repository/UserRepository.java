package com.stackroute.repository;

import com.stackroute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
     User findByEmailIdAndPassword(String emailId, String password);
     User findByEmailId(String emailId);
}

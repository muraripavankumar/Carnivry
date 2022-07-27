package com.stackroute.repository;

import com.stackroute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
     User findByEmailAndPassword(String email, String password);
     User findByEmail(String email);
}

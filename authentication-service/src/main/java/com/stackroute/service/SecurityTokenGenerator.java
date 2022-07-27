package com.stackroute.service;



import com.stackroute.model.User;
import io.jsonwebtoken.MalformedJwtException;

import java.util.Map;

public interface SecurityTokenGenerator {
      Map<String, String> generateToken(User user) throws MalformedJwtException;

}

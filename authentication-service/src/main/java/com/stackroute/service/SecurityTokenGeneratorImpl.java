package com.stackroute.service;



import com.stackroute.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class SecurityTokenGeneratorImpl implements SecurityTokenGenerator {
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Override
    public Map<String, String> generateToken(User user) {




        String jwttoken = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256,"mykey").compact();

        Map<String,String> map = new HashMap<>();
        map.put("token",jwttoken);
        map.put("message","Token generated successfully");
        return map;
    }
}



























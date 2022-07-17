package com.stackroute.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenStore {

    private final Map< String, Authentication> cache = new HashMap<>();

    private static  final int EXPIRATION_TIME = 60;



    public String generateToken( Authentication authentication ) {

//        Principal principal= (Principal) authentication.getPrincipal();
//        System.out.println(principal.getName());

        String principal= authentication.getPrincipal().toString();
//        System.out.println(principal);
        int sindex= principal.indexOf(" name=");
        int eindex= principal.indexOf(",",sindex);
        String name= principal.substring(sindex+6,eindex);
        System.out.println(name);


        String token = generateToken(name);
//        System.out.println(token);
        cache.put( token, authentication );
        return token;
    }

    public Authentication getAuth( String token ) {
        return cache.getOrDefault( token, null );
    }

    public String generateToken(String username) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(calendar.getTime().getTime()))
                .signWith(SignatureAlgorithm.HS512, "myKey")
                .compact();
    }
}
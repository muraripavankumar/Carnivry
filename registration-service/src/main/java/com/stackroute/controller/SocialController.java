package com.stackroute.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/SocialUser")
@Slf4j
public class SocialController {
    @GetMapping("/getName")
    public ResponseEntity<?> getName(@AuthenticationPrincipal OAuth2User principal)
    {
        try {
            String name= principal.getAttribute("name");
            log.info("User name {} returned",name);
            return new ResponseEntity<>(Collections.singletonMap("name", name), HttpStatus.OK);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getEmail")
    public ResponseEntity<?> getEmail(@AuthenticationPrincipal OAuth2User principal)
    {
        System.out.println(principal.toString());
        try {
            String email= principal.getAttribute("email");
            log.info("User email id {} returned ",email);
            return new ResponseEntity<>( Collections.singletonMap("email",email ),HttpStatus.OK);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getGithubUsername")
    public ResponseEntity<?> getGithubUsername(@AuthenticationPrincipal OAuth2User principal){
        try {
            String username= principal.getAttribute("login");
            log.info("Github username {} returned ",username);
            return new ResponseEntity<>( Collections.singletonMap("username",username ),HttpStatus.OK);
        }catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getGithubAvatar")
    public ResponseEntity<?> getGithubAvatar(@AuthenticationPrincipal OAuth2User principal){
        try {
            log.info("Github avatar url returned");
            return new ResponseEntity<>(Collections.singletonMap("github_avatar", principal.getAttribute("avatar_url"))
                                        ,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getGoogleAvatar")
    public ResponseEntity<?> getGoogleAvatar(@AuthenticationPrincipal OAuth2User principal){
        try {
            log.info("Google avatar url returned");
            return new ResponseEntity<>(Collections.singletonMap("google_avatar", principal.getAttribute("picture"))
                    ,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Internal server error {}",e.getMessage());
            return new ResponseEntity<>("Unknown error occurred. Will fix this soon.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

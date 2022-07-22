package com.stackroute.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/Carnivry/SocialUser")
public class SocialController {
    @GetMapping("/getName")
    public Map<String,String> getName(@AuthenticationPrincipal OAuth2User principal)
    {
        System.out.println(principal.toString());

        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    @GetMapping("/getEmail")
    public Map<String,String> getEmail(@AuthenticationPrincipal OAuth2User principal)
    {
        System.out.println(principal.toString());
        return Collections.singletonMap("email",principal.getAttribute("email"));
    }

    @GetMapping("/getGithubUsername")
    public Map<String,String> getGithubUsername(@AuthenticationPrincipal OAuth2User principal){
        return Collections.singletonMap("username", principal.getAttribute("login"));
    }

    @GetMapping("/getGithubAvatar")
    public Map<String,String> getGithubAvatar(@AuthenticationPrincipal OAuth2User principal){
        return Collections.singletonMap("github_avatar", principal.getAttribute("avatar_url"));
    }

    @GetMapping("/getGoogleAvatar")
    public Map<String,String> getGoogleAvatar(@AuthenticationPrincipal OAuth2User principal){
        return Collections.singletonMap("google_avatar", principal.getAttribute("picture"));
    }

}

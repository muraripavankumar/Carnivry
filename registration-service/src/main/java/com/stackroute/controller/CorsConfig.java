///**
// * Created by Shanmukha
// * Date 12-08-2022
// * Time 14:27
// */
//package com.stackroute.controller;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig {
//    private static final String GET = "GET";
//    private static final String POST = "POST";
//    private static final String DELETE = "DELETE";
//    private static final String PUT = "PUT";
//    private static final String PATCH = "PATCH";
//
//    public WebMvcConfigurer corsConfigurer(){
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//
//                registry.addMapping("/**")
//                        .allowedMethods(GET, PUT, POST, DELETE, PATCH)
//                        .allowedHeaders("*")
//                        .allowedOriginPatterns("*")
//                        .allowCredentials(true)
//                ;
//
//            }
//
//        };
//    }
//
//}

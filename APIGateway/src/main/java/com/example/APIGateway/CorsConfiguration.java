package com.example.APIGateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.Collections;

//@Configuration
public class CorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
        final org.springframework.web.cors.CorsConfiguration corsConfig= new org.springframework.web.cors.CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE"));
        corsConfig.addAllowedHeader("*");
        final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfig);
        return new CorsWebFilter(source);
    }
}

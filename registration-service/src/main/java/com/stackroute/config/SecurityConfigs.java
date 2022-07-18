package com.stackroute.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter {


    private final ObjectMapper mapper;
    private final TokenStore tokenStore;
    private final TokenFilter tokenFilter;

    private static final String[] WHITE_LIST_URLS = {

            "/Carnivry/register",
            "/Carnivry/register/socialLogin",
            "/Carnivry/saveGenres",
            "/Carnivry/verifyRegistration*",
            "/Carnivry/resendVerifyToken*",
            "/Carnivry/emailVerifiedStatus/*",
            "/Carnivry/saveGenres",
            "/Carnivry/allGenres" ,
            "/Carnivry/checkUser/**",
            "/oauth2/**", "/login**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    public SecurityConfigs( ObjectMapper mapper, TokenStore tokenStore,
                            TokenFilter tokenFilter ) {
        this.mapper = mapper;
        this.tokenStore = tokenStore;
        this.tokenFilter = tokenFilter;
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers( WHITE_LIST_URLS ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository( new InMemoryRequestRepository() )
                .and()
                .successHandler( this::successHandler )
                .and()
                .exceptionHandling()
                .authenticationEntryPoint( this::authenticationEntryPoint )
                .and().logout(cust -> cust.addLogoutHandler( this::logout ).logoutSuccessHandler( this::onLogoutSuccess ));
        http.addFilterBefore( tokenFilter, UsernamePasswordAuthenticationFilter.class );
    }

    private void logout(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) {
        // You can process token here
        System.out.println("Auth token is - " + request.getHeader( "Authorization" ));
    }

    void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                         Authentication authentication) throws IOException, ServletException {
        // this code is just sending the 200 ok response and preventing redirect
        response.setStatus( HttpServletResponse.SC_OK );
    }



    private void successHandler(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication ) throws IOException {
        String token = tokenStore.generateToken( authentication );
        response.getWriter().write(
                mapper.writeValueAsString( Collections.singletonMap( "accessToken", token ) )
        );
    }

    private void authenticationEntryPoint( HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException authException ) throws IOException {
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        response.getWriter().write( mapper.writeValueAsString( Collections.singletonMap( "error", "Unauthenticated" ) ) );
    }
}
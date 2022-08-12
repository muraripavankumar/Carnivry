package com.example.APIGateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//public class JwtFilter extends GenericFilterBean {
public class JwtFilter{

//    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String authHeader =request.getHeader("Authorization");
        //if the method is options request, then no validation required
        if(request.getMethod().equalsIgnoreCase("options")){
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request,response);
        }
        else if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid token");
        }
        else{ // request coming with authorization header
            String token = authHeader.substring(7);
            //parse the payload of token
            Claims claims = Jwts.parser().setSigningKey("mykey").parseClaimsJws(token).getBody();
            log.info("Claims : "+claims);
            request.setAttribute("claims",claims);
            filterChain.doFilter(request,response);
        }
    }
}

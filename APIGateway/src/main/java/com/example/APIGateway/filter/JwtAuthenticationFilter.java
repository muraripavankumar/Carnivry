//package com.example.APIGateway.filter;
//
//import com.example.APIGateway.exception.JwtTokenMalformendException;
//import com.example.APIGateway.exception.JwtTokenMissingException;
//import io.jsonwebtoken.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//@Slf4j
////@Component
//public class JwtAuthenticationFilter implements GatewayFilter {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request= (ServerHttpRequest) exchange.getRequest();
//        final List<String> apiEndpoints= List.of("/api");
//        Predicate<ServerHttpRequest> isApiSecured=r-> apiEndpoints.stream().noneMatch(uri-> r.getURI().getPath().contains(uri));
//        if(isApiSecured.test(request)){
//            if(!request.getHeaders().containsKey("Authorization")){
//                ServerHttpResponse response=exchange.getResponse();
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                log.info("HttpStatus UNAUTHORIZED");
//                return response.setComplete();
//            }
//            final String token =request.getHeaders().getOrEmpty("Authorization").get(0);
//            try {
//                validateToken(token);
//            } catch (JwtTokenMalformendException | JwtTokenMissingException e) {
//                ServerHttpResponse response=exchange.getResponse();
//                response.setStatusCode(HttpStatus.BAD_REQUEST);
//                return response.setComplete();
//            }
//            Claims claims=getClaims(token);
//            exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
//
//
//        }
//        return chain.filter(exchange);
//    }
//    private void validateToken(final String token) throws JwtTokenMalformendException, JwtTokenMissingException {
//        try {
//            Jwts.parser().setSigningKey("mykey").parseClaimsJws(token);
//        } catch (ExpiredJwtException e) {
//            throw new JwtTokenMalformendException("Expired JWT token");
//        } catch (UnsupportedJwtException e) {
//            throw new JwtTokenMalformendException("Unsupported JWT token");
//        } catch (MalformedJwtException e) {
//            throw new JwtTokenMalformendException("Invalid JWT token");
//        } catch (SignatureException e) {
//            throw new JwtTokenMalformendException("Invalid JWT signature");
//        } catch (IllegalArgumentException e) {
//            throw new JwtTokenMissingException("JWT claims string is empty");
//        }
//    }
//    private Claims getClaims(final String token){
//        try {
//            Claims body = Jwts.parser().setSigningKey("mykey").parseClaimsJws(token).getBody();
//            return body;
//        }catch (Exception e){
//            log.error("Exception occurred in JwtAuthenticationFilter -> getClaims() {}",e.getStackTrace());
//
//        }
//        return null;
//    }
//}

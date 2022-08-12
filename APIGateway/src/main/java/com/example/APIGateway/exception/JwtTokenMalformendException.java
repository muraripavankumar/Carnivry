package com.example.APIGateway.exception;

public class JwtTokenMalformendException extends Exception{
    public JwtTokenMalformendException(String message) {
        super (message);
    }
}

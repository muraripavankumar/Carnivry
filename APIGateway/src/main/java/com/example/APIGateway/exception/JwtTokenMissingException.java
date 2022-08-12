package com.example.APIGateway.exception;

public class JwtTokenMissingException extends Exception{
    public JwtTokenMissingException(String message) {
        super(message);
    }
}

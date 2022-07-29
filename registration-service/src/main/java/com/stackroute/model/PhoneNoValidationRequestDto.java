package com.stackroute.model;

import lombok.Data;

@Data
public class PhoneNoValidationRequestDto {

    private String phoneNumber;//destination
    private String email;
    private String oneTimePassword;
}

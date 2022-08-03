package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegModel {
    @NotEmpty
    @Size(min=4, message = "Name should be atleast 4 characters")
    private String name;
    @NotEmpty
    @Email
    private String email;

    private Date dob;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String password, matchingPassword;

}

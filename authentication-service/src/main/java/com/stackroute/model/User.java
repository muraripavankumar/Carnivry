package com.stackroute.model;

import com.stackroute.config.AesEncryptor;
import lombok.*;


import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @NotNull
    private String email;
    @Convert(converter = AesEncryptor.class)
    @NotNull
    private String password;




}

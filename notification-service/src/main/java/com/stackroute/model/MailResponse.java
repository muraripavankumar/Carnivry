package com.stackroute.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailResponse {
    private String message;
    private boolean status;
}

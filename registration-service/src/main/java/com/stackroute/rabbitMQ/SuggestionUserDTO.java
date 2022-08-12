package com.stackroute.rabbitMQ;


import com.stackroute.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SuggestionUserDTO {
    private String emailId;
    private String name;
    private List<String> wishlist;
    private List<Genre> likedGenre;
    private String city;
}

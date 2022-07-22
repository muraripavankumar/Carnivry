package com.example.SuggestionService.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NodeEntity
public class User {
    @GraphId
    @Id
    private String emailId;
    private String name;
    private List<String> wishlist;
    private List<String> likedGenre;
//    private List<String> likedArtists;
//    private Preference preference;
//    private Address address;
    private String houseNo;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private int pincode;
}
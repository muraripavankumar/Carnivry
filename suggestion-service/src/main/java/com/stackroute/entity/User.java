package com.stackroute.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
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
    private String city;
}
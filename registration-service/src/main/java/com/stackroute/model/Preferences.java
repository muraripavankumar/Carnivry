package com.stackroute.model;

import com.stackroute.entity.CarnivryUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preferences {

    Set<Genre> likedGenres;
    Set<CarnivryUser> likedArtists;

}

package com.stackroute.model;

import com.stackroute.entity.CarnivryUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preferences {

    List<Genre> likedGenres;
    List<CarnivryUser> likedArtists;
}

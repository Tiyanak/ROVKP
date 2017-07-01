/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.tel.fer.rovk.topic8.example2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class Movie {
  
    private final long id;
    private final String title;
    private final Set<String> genres;

    public Movie(String record) throws Exception {
        String[] splitted = record.split(",");
        id = Long.parseLong(splitted[0]);
        title = splitted[1];         
        genres = new HashSet<>();
        Collections.addAll(genres, splitted[2].split(Pattern.quote("|")));
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getGenres() {
        return genres;
    }
}

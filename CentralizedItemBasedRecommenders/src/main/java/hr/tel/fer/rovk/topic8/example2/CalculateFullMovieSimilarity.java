/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.tel.fer.rovk.topic8.example2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class CalculateFullMovieSimilarity {

    public static void main(String[] args) {

        List<Movie> movies = new LinkedList<>();

        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(new FileInputStream("/home/kpripuzic/Magnetic/movielens/ml-latest-small/movies.csv")))) {

            //ignore the header
            String record = buffer.readLine();

            while ((record = buffer.readLine()) != null) {
                try {
                    Movie movie = new Movie(record);
                    movies.add(movie);
                } catch (Exception ex) {
                    System.out.println("Cannot parse: " + record + "due to the " + ex);
                }
            }

            //save the results
            try (BufferedWriter writter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("/home/kpripuzic/Magnetic/movielens/ml-latest-small/item-similarity.csv")))) {
                int i = 0;
                for (Movie movie1 : movies) {
                    int j = 0;
                    for (Movie movie2 : movies) {
                        if (j > i) {
                            //similarities are symmetric
                            Set<String> intersection = new HashSet<>(movie1.getGenres());
                            intersection.retainAll(movie2.getGenres());
                            float similarity = intersection.size()
                                    / (float) Math.max(movie1.getGenres().size(), movie2.getGenres().size());
                            if (similarity != 0) {
                                writter.append(movie1.getId() + "," + movie2.getId() + "," + similarity + "\n");
                            }
                        }
                        j++;
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

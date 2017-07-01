/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.tel.fer.rovk.topic8.example2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class ShortMovieSimilarity implements ItemSimilarity {

    Map<Long, TreeSet<MovieSimilarity>> map;
    private int k;

    public ShortMovieSimilarity(String path, int k) {
        this.k = k;
        this.map = new HashMap<>();
        
        List<Movie> movies = new LinkedList<>();

        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path)))) {

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

            //fill the map of the most similar items
            for (Movie movie1 : movies) {
                for (Movie movie2 : movies) {
                    if (movie1.getId() != movie2.getId()) {
                        //retrive the most similar objects of movie1
                        TreeSet<MovieSimilarity> similarObjects = map.get(movie1.getId());
                        if (similarObjects == null) {
                            similarObjects = new TreeSet<>();
                            map.put(movie1.getId(), similarObjects);
                        }

                        //calculate the similarity
                        Set<String> intersection = new HashSet<>(movie1.getGenres());
                        intersection.retainAll(movie2.getGenres());
                        double similarity = intersection.size()
                                / (double) Math.max(movie1.getGenres().size(), movie2.getGenres().size());

                        //add the new movie similarity, poll the worst movie similarity
                        similarObjects.add(new MovieSimilarity(movie2.getId(), similarity));
                        if (similarObjects.size() > k) {
                            similarObjects.pollFirst();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double itemSimilarity(long itemID1, long itemID2) throws TasteException {
        TreeSet<MovieSimilarity> similarObjects = map.get(itemID1);
        if (similarObjects == null) {
            return 0;
        } else {
            for (MovieSimilarity ms : similarObjects) {
                if (ms.movieID == itemID2) {
                    return ms.similarity;
                }
            }
            return 0;
        }
    }

    @Override
    public double[] itemSimilarities(long itemID1, long[] itemID2s) throws TasteException {
        double[] result = new double[itemID2s.length];
        for (int i = 0; i < itemID2s.length; i++) {
            result[i] = itemSimilarity(itemID1, itemID2s[i]);
        }
        return result;
    }

    @Override
    public long[] allSimilarItemIDs(long itemID) throws TasteException {
        long[] result = new long[k];
        int i = 0;
        for (MovieSimilarity ms : map.get(itemID)) {
            result[i++] = ms.movieID;
        }
        return result;
    }

    private class MovieSimilarity implements Comparable<MovieSimilarity> {

        private final long movieID;
        private final double similarity;

        public MovieSimilarity(long movieID, double similarity) {
            this.movieID = movieID;
            this.similarity = similarity;
        }

        @Override
        public int compareTo(MovieSimilarity other) {
            if (this.similarity < other.similarity) {
                return -1;
            } else if (this.similarity < other.similarity) {
                return 1;
            } else {
                return Integer.compare(this.hashCode(), other.hashCode());
            }
        }
    }
}

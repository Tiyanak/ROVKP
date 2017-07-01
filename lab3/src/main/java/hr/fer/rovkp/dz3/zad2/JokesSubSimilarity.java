package hr.fer.rovkp.dz3.zad2;

/**
 * Created by Igor Farszky on 19.5.2017..
 */
public class JokesSubSimilarity implements Comparable<JokesSubSimilarity>{

    private Long jokeid;
    private Double similarity;

    public JokesSubSimilarity(Long jokeid, Double similarity) {
        this.jokeid = jokeid;
        this.similarity = similarity;
    }

    public Long getJokeid() {
        return jokeid;
    }

    public Double getSimilarity() {
        return similarity;
    }

    @Override
    public int compareTo(JokesSubSimilarity o) {
        return this.similarity.compareTo(o.getSimilarity());
    }
}

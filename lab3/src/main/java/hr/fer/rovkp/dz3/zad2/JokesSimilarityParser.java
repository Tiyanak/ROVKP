package hr.fer.rovkp.dz3.zad2;

/**
 * Created by Igor Farszky on 19.5.2017..
 */
public class JokesSimilarityParser {

    private Integer joke1id;
    private Integer joke2id;
    private Double similarity;

    public JokesSimilarityParser() {
    }

    public JokesSimilarityParser parse(String record){
        String[] splitted = record.split(",");
        this.joke1id = Integer.parseInt(splitted[0]);
        this.joke2id = Integer.parseInt(splitted[1]);
        this.similarity = Double.parseDouble(splitted[2]);

        return this;
    }

    public Integer getJoke1id() {
        return joke1id;
    }

    public void setJoke1id(Integer joke1id) {
        this.joke1id = joke1id;
    }

    public Integer getJoke2id() {
        return joke2id;
    }

    public void setJoke2id(Integer joke2id) {
        this.joke2id = joke2id;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
}

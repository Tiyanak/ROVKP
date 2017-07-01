package hr.fer.rovkp.dz3;

import java.io.File;

/**
 * Created by Igor Farszky on 19.5.2017..
 */
public class JesterRatingsParser {

    private int userid;
    private int jokeid;
    private double rating;

    public JesterRatingsParser(String record) {
        String[] splitted = record.split("\\t+");

        this.userid = Integer.parseInt(splitted[0]);
        this.jokeid = Integer.parseInt(splitted[1]);
        this.rating = Double.parseDouble(splitted[2]);
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getJokeid() {
        return jokeid;
    }

    public void setJokeid(int jokeid) {
        this.jokeid = jokeid;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic4.example2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Ivana
 */
public class DEBSRecordParser {

    private String input;
    private int weekDay;
    private final SimpleDateFormat f = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");

    public void parse(String record) throws Exception {
        input = record;
        String[] splitted = record.split(",");
        String date = splitted[5];

        Calendar c = Calendar.getInstance();
        c.setTime(f.parse(date));
        weekDay = c.get(Calendar.DAY_OF_WEEK);

    }

    public String getInput() {
        return input;
    }

    public int getWeekDay() {
        return weekDay;
    }

}

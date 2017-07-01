/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic4.example3;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import hr.fer.tel.rovkp.topic4.example2.DEBSRecordParser;

/**
 *
 * @author Ivana
 */
public class WeekDayPartitionMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        DEBSRecordParser parser = new DEBSRecordParser();

        //skip the first line
        if (key.get() > 0) {
            String record = value.toString();
            try {
                parser.parse(record);
                context.write(new IntWritable(parser.getWeekDay()), new Text(parser.getInput()));
            } catch (Exception ex) {
                System.out.println("Cannot parse: " + record + "due to the " + ex);
            }
        }
    }
}

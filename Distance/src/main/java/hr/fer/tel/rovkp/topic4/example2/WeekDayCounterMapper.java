/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic4.example2;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author Ivana
 */
public class WeekDayCounterMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        DEBSRecordParser parser = new DEBSRecordParser();

        //skip the first line
        if (key.get() > 0) {
            String record = value.toString();
            try {
                parser.parse(record);
                switch (parser.getWeekDay()) {
                    case 1:
                        context.getCounter(WeekDayWithCounters.COUNTERS.SUNDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                    case 2:
                        context.getCounter(WeekDayWithCounters.COUNTERS.MONDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                    case 3:
                        context.getCounter(WeekDayWithCounters.COUNTERS.TUESDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                    case 4:
                        context.getCounter(WeekDayWithCounters.COUNTERS.WEDNESDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                    case 5:
                        context.getCounter(WeekDayWithCounters.COUNTERS.THURSDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                    case 6:
                        context.getCounter(WeekDayWithCounters.COUNTERS.FRIDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                    default:
                        context.getCounter(WeekDayWithCounters.COUNTERS.SATURDAY).increment(1L);
                        //context.write(new IntWritable(parser.getWeekDay()), NullWritable.get());
                        break;
                }

            } catch (Exception ex) {
                System.out.println("Cannot parse: " + record + "due to the " + ex);

                context.getCounter(WeekDayWithCounters.COUNTERS.DAY).increment(1L);
            }
        }
    }
}

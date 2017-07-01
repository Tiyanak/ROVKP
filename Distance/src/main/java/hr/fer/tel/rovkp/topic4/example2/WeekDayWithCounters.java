/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic4.example2;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Ivana
 */
public class WeekDayWithCounters {

    public enum COUNTERS {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
        DAY
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: hr.fer.tel.rovkp.topic4.example3.WeekDayWithCounters <input path> <output path>");
            System.exit(-1);
        }
        Job job = Job.getInstance();
        job.setJarByClass(WeekDayWithCounters.class);
        job.setJobName("TaxiWeekDayWithCounters Example");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(WeekDayCounterMapper.class);
        job.setNumReduceTasks(0);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        job.waitForCompletion(true);
        Counters counters = job.getCounters();

        System.out.println("Monday: " + counters.findCounter(COUNTERS.MONDAY).getValue());
        System.out.println("Tuesday: " + counters.findCounter(COUNTERS.TUESDAY).getValue());
        System.out.println("Wednesday: " + counters.findCounter(COUNTERS.WEDNESDAY).getValue());
        System.out.println("Thursday: " + counters.findCounter(COUNTERS.THURSDAY).getValue());
        System.out.println("Friday: " + counters.findCounter(COUNTERS.FRIDAY).getValue());
        System.out.println("Saturday: " + counters.findCounter(COUNTERS.SATURDAY).getValue());
        System.out.println("Sunday: " + counters.findCounter(COUNTERS.SUNDAY).getValue());
        System.out.println("UNSPECIFIED: " + counters.findCounter(COUNTERS.DAY).getValue());
    }
}

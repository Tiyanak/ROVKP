/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic4.example3;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Ivana
 */
public class WeekDayPartitionerExample {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: hr.fer.tel.rovkp.topic4.example3.WeekDayPartitioner <input path> <output path>");
            System.exit(-1);
        }

        Job job = Job.getInstance();
        job.setJarByClass(WeekDayPartitionerExample.class);
        job.setJobName("WeekDayPartitioner Example");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(WeekDayPartitionMapper.class);
        job.setPartitionerClass(WeekDayPartitioner.class);
        job.setReducerClass(WeekDayPartitionReducer.class);
        job.setNumReduceTasks(7);

        //job.setMapOutputKeyClass(IntWritable.class);
        //job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}

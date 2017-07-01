package hr.fer.tel.rovkp.topic4.example1;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Ivana
 */
public class AverageDistance {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: AverageDistance <input path> <output path>");
            System.exit(-1);
        }

        Job job = Job.getInstance();
        job.setJarByClass(AverageDistance.class);
        job.setJobName("Average distance");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(AverageDistanceMapper.class);
        job.setCombinerClass(AverageDistanceReducer.class);
        job.setReducerClass(AverageDistanceReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(CountAverageTuple.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

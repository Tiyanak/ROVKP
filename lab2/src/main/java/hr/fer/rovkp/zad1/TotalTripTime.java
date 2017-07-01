package hr.fer.rovkp.zad1;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Igor Farszky on 3.4.2017..
 */
public class TotalTripTime {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        if(args.length != 2){
            System.err.println("Usage: hr.fer.rovkp.zad1.TotalTripTime <input> <output>");
            System.exit(1);
        }

        Job job = Job.getInstance();

        job.setJarByClass(TotalTripTime.class);
        job.setJobName("Total trip time");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(TotalTripTimeMapper.class);
        job.setCombinerClass(TotalTripTimeReducer.class);
        job.setReducerClass(TotalTripTimeReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TripTimeModel.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TripTimeModel.class);

        job.waitForCompletion(true);

        System.out.println("JOB FINISHER: " + ((double)job.getFinishTime() - job.getStartTime()) / 1000);

    }

}

package hr.fer.rovkp.zad2;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Igor Farszky on 4.4.2017..
 */
public class VrsteVoznji {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        if(args.length != 2){
            System.err.println("Usage: hr.fer.rovkp.zad2.VrsteVoznji <input> <output>");
            System.exit(1);
        }

        Job job = Job.getInstance();
        job.setJarByClass(VrsteVoznji.class);
        job.setJobName("Vrste voznji");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(VrsteVoznjiMapper.class);
        job.setPartitionerClass(VrsteVoznjiPartitioner.class);
        job.setReducerClass(VrsteVoznjiReducer.class);
        job.setNumReduceTasks(6);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(VrsteVoznjiModel.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.waitForCompletion(true);

        System.out.println("JOB FINISHER: " + ((double)job.getFinishTime() - job.getStartTime()) / 1000);
    }

}

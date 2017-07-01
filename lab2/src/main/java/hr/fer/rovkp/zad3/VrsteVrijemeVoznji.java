package hr.fer.rovkp.zad3;

import hr.fer.rovkp.zad1.TotalTripTimeMapper;
import hr.fer.rovkp.zad1.TotalTripTimeReducer;
import hr.fer.rovkp.zad1.TripTimeModel;
import hr.fer.rovkp.zad2.VrsteVoznjiMapper;
import hr.fer.rovkp.zad2.VrsteVoznjiModel;
import hr.fer.rovkp.zad2.VrsteVoznjiPartitioner;
import hr.fer.rovkp.zad2.VrsteVoznjiReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Igor Farszky on 5.4.2017..
 */
public class VrsteVrijemeVoznji {

    private static final String INTERMEDIATE_PATH = "intermediate";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        if(args.length != 2){
            System.err.println("Usage: hr.fer.rovkp.zad3.VrsteVrijemeVoznji <input> <output>");
            System.exit(1);
        }

        Job adJob = Job.getInstance();
        adJob.setJarByClass(VrsteVrijemeVoznji.class);
        adJob.setJobName("Partitioning");

        FileInputFormat.addInputPath(adJob, new Path(args[0]));
        FileOutputFormat.setOutputPath(adJob, new Path(INTERMEDIATE_PATH));

        adJob.setMapperClass(VrsteVoznjiMapper.class);
        adJob.setPartitionerClass(VrsteVoznjiPartitioner.class);
        adJob.setReducerClass(VrsteVoznjiReducer.class);
        adJob.setNumReduceTasks(6);

        adJob.setMapOutputKeyClass(Text.class);
        adJob.setMapOutputValueClass(VrsteVoznjiModel.class);
        adJob.setOutputKeyClass(Text.class);
        adJob.setOutputValueClass(NullWritable.class);

        int code = adJob.waitForCompletion(true) ? 0 : 1;

        System.out.println("FIRST MAPREDUCER: " + ((double)adJob.getFinishTime()-adJob.getStartTime()) / 1000);

        if (code == 0) {
            Job bJob = Job.getInstance();
            bJob.setJarByClass(VrsteVrijemeVoznji.class);
            bJob.setJobName("Total trip time");

            FileInputFormat.addInputPath(bJob, new Path(INTERMEDIATE_PATH));
            FileOutputFormat.setOutputPath(bJob, new Path(args[1]));

            bJob.setMapperClass(TotalTripTimeMapper.class);
            bJob.setReducerClass(TotalTripTimeReducer.class);

            bJob.setMapOutputValueClass(Text.class);
            bJob.setMapOutputValueClass(TripTimeModel.class);
            bJob.setOutputKeyClass(Text.class);
            bJob.setOutputValueClass(TripTimeModel.class);

            code = bJob.waitForCompletion(true) ? 0 : 1;
        }

        System.out.println("SECOND MAPREDUCER: " + ((double)adJob.getFinishTime()-adJob.getStartTime()) / 1000);

        Configuration conf = new Configuration();
        FileSystem.get(conf).delete(new Path(INTERMEDIATE_PATH), true);

        System.exit(code);

    }

}

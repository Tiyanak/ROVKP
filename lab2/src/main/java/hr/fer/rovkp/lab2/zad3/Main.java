package hr.fer.rovkp.lab2.zad3;

import hr.fer.rovkp.lab2.zad2.Maper;
import hr.fer.rovkp.lab2.zad2.MojPartitioner;
import hr.fer.rovkp.lab2.zad2.MojReducer;
import hr.fer.rovkp.zad1.TotalTripTimeMapper;
import hr.fer.rovkp.zad2.VrsteVoznjiMapper;
import hr.fer.rovkp.zad3.VrsteVrijemeVoznji;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * Created by Igor Farszky on 17.4.2017..
 */
public class Main {

    private static final String INTERMEDIATE_PATH = "/user/rovkp/ifarszky/intermediate";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        if(args.length != 2){
            System.err.println("Usage: hr.fer.rovkp.zad3.VrsteVrijemeVoznji <input> <output>");
            System.exit(1);
        }

        Configuration conf = new Configuration();

        Job filtarJob = Job.getInstance(conf, "Filtriranje");
        filtarJob.setJarByClass(Main.class);

        FileInputFormat.addInputPath(filtarJob, new Path(args[0]));
        FileOutputFormat.setOutputPath(filtarJob, new Path(INTERMEDIATE_PATH));

        filtarJob.setMapperClass(FiltarMaper.class);
        filtarJob.setNumReduceTasks(0);

        filtarJob.setOutputKeyClass(Text.class);
        filtarJob.setOutputValueClass(NullWritable.class);

        //configure the multiple outputs
        MultipleOutputs.addNamedOutput(filtarJob, "bins", TextOutputFormat.class, Text.class, NullWritable.class);

        int code = filtarJob.waitForCompletion(true) ? 0 : 1;

        System.out.println("FIRST MAPREDUCER: " + ((double)filtarJob.getFinishTime()-filtarJob.getStartTime()) / 1000);

        if (code == 0) {
            Job satJob = Job.getInstance(conf, "Rastavi po satima");
            satJob.setJarByClass(Main.class);

            FileInputFormat.addInputPath(satJob, new Path(INTERMEDIATE_PATH));
            FileOutputFormat.setOutputPath(satJob, new Path(args[1]));

            satJob.setMapperClass(Maper.class);
            satJob.setPartitionerClass(MojPartitioner.class);
            satJob.setReducerClass(MojReducer.class);
            satJob.setNumReduceTasks(24);

            satJob.setOutputKeyClass(Text.class);
            satJob.setOutputValueClass(NullWritable.class);

            code = satJob.waitForCompletion(true) ? 0 : 1;

            System.out.println("SECOND MAPREDUCER: " + ((double)satJob.getFinishTime()-satJob.getStartTime()) / 1000);
        }

        System.exit(code);

    }
}

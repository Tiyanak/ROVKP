package hr.fer.tel.rovkp.topic6.example7;

import java.io.IOException;
import java.util.function.Predicate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ParallelDriver {

    public enum CounterId {
        WITH_PREDICATE,
        WITHOUT_PREDICATE,        
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.println("Usage: ParallelDriver <input path1> <input path2> <output path1> <output path2>");
            System.exit(-1);
        }

        //initialize and run the first job
        Predicate<Double> predicate1 = new Predicate<Double>() {
            @Override
            public boolean test(Double t) {
                return t < 1;
            }
        };
        Job firstJob = initializeJob("Parallel Example - count below2",
                args[0], args[2], predicate1);
        firstJob.submit();

        //initialize and run the second job
        Predicate<Double> predicate2 = new Predicate<Double>() {
            @Override
            public boolean test(Double t) {
                return t > 2.5;
            }
        };
        Job secondJob = initializeJob("Parallel Example - count above2",
                args[1], args[3], predicate2);
        secondJob.submit();

        // sleep while both jobs are not finished
        while (!firstJob.isComplete() || !firstJob.isComplete()) {
            Thread.sleep(5000);
        }

        if (firstJob.isSuccessful()) {
            System.out.println("First job completed successfully!");
            System.out.println("below2 and <1: " + firstJob.getCounters().findCounter(CounterId.WITH_PREDICATE).getValue());
            System.out.println("below2 and >=1: " + firstJob.getCounters().findCounter(CounterId.WITHOUT_PREDICATE).getValue());
        } else {
            System.out.println("First average job failed!");
        }

        if (secondJob.isSuccessful()) {
            System.out.println("Second job completed successfully!");
            System.out.println("above2 and >2.5: " + secondJob.getCounters().findCounter(CounterId.WITH_PREDICATE).getValue());
            System.out.println("above2 and <=2.5: " + secondJob.getCounters().findCounter(CounterId.WITHOUT_PREDICATE).getValue());
        } else {
            System.out.println("Second average job failed!");
        }

        System.exit(firstJob.isSuccessful() && secondJob.isSuccessful() ? 0 : 1);
    }

    private static Job initializeJob(String name, String inputDir, String outputDir, 
            Predicate<Double> predicate)
            throws IllegalArgumentException, IOException, IllegalStateException {

        Configuration conf = new Configuration();
        conf.setClass("predicate", predicate.getClass(), Predicate.class);
        Job job = Job.getInstance(conf);

        job.setJarByClass(ParallelDriver.class);
        job.setJobName(name);

        FileInputFormat.addInputPath(job, new Path(inputDir));
        FileOutputFormat.setOutputPath(job, new Path(outputDir));

        job.setMapperClass(CounterMapper.class);
        job.setNumReduceTasks(0);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(NullWritable.class);

        return job;
    }
}

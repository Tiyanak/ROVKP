package hr.fer.tel.rovkp.topic6.example6;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class SequentialDriver {

    private static final String INTERMEDIATE_PATH = "intermediate";

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: SequentialDriver <input path> <output path>");
            System.exit(-1);
        }

        Job adJob = Job.getInstance();
        adJob.setJarByClass(SequentialDriver.class);
        adJob.setJobName("Sequential Example - Distance");

        FileInputFormat.addInputPath(adJob, new Path(args[0]));
        FileOutputFormat.setOutputPath(adJob, new Path(INTERMEDIATE_PATH));

        adJob.setMapperClass(AverageDistanceMapper.class);
        adJob.setReducerClass(AverageDistanceReducer.class);

        adJob.setMapOutputKeyClass(Text.class);
        adJob.setMapOutputValueClass(CountAverageTuple.class);
        adJob.setOutputKeyClass(Text.class);
        adJob.setOutputValueClass(DoubleWritable.class);

        int code = adJob.waitForCompletion(true) ? 0 : 1;

        if (code == 0) {
            Job bJob = Job.getInstance();
            bJob.setJarByClass(SequentialDriver.class);
            bJob.setJobName("Sequential Example - Binning");

            FileInputFormat.addInputPath(bJob, new Path(INTERMEDIATE_PATH));
            FileOutputFormat.setOutputPath(bJob, new Path(args[1]));

            bJob.setMapperClass(BinningMapper.class);
            bJob.setNumReduceTasks(0);

            bJob.setOutputKeyClass(Text.class);
            bJob.setOutputValueClass(DoubleWritable.class);

            //configure the multiple outputs
            MultipleOutputs.addNamedOutput(bJob, "bins", TextOutputFormat.class, Text.class, DoubleWritable.class);

            code = bJob.waitForCompletion(true) ? 0 : 1;
        }

        System.exit(code);
    }
}

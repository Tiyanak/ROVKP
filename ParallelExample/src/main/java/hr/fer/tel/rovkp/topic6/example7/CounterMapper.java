package hr.fer.tel.rovkp.topic6.example7;

import hr.fer.tel.rovkp.topic6.example7.ParallelDriver.CounterId;
import java.io.IOException;
import java.util.function.Predicate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class CounterMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {

    private Predicate<Double> predicate = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        Configuration conf = context.getConfiguration();
        Class predicateClass = conf.getClass("predicate", null);
        try {
            predicate = (Predicate<Double>) predicateClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Cannot load the predicate");
        }
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        double avgDistance = Double.parseDouble(value.toString().substring(32));

        if (predicate != null && predicate.test(avgDistance)) {
            context.getCounter(CounterId.WITH_PREDICATE).increment(1);
        } else {
            context.getCounter(CounterId.WITHOUT_PREDICATE).increment(1);
        }
    }
}

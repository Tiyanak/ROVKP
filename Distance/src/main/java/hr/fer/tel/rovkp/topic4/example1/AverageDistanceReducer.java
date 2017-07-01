package hr.fer.tel.rovkp.topic4.example1;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author Ivana
 */
public class AverageDistanceReducer
        extends Reducer<Text, CountAverageTuple, Text, CountAverageTuple> {

    private CountAverageTuple result = new CountAverageTuple();

    @Override
    public void reduce(Text key, Iterable<CountAverageTuple> values, Context context)
            throws IOException, InterruptedException {
        double sum = 0;
        int count = 0;
        for (CountAverageTuple value : values) {
            count += value.getCount();
            sum += value.getCount() * value.getAverage();
        }
        result.set(new IntWritable(count), new DoubleWritable(sum / count));
        context.write(key, result);
    }
}

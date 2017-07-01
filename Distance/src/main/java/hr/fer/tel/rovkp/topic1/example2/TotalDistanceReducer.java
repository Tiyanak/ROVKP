package hr.fer.tel.rovkp.topic1.example2;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TotalDistanceReducer
        extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        double totalDistance = 0;
        for (DoubleWritable value : values) {
            totalDistance += value.get();
        }
        context.write(key, new DoubleWritable(totalDistance));
    }
}

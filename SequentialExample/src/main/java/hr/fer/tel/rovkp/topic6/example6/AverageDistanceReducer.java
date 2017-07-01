package hr.fer.tel.rovkp.topic6.example6;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageDistanceReducer
        extends Reducer<Text, CountAverageTuple, Text, DoubleWritable> {
    
    @Override
    public void reduce(Text key, Iterable<CountAverageTuple> values, Context context) throws IOException, InterruptedException {
        double sum = 0;
        long count = 0;
        
        for (CountAverageTuple value : values) {
            sum += value.getCount() * value.getAverage();
            count += value.getCount();
        }
        
        context.write(key, new DoubleWritable(sum / count));
    }
}

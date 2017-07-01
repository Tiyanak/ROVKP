package hr.fer.tel.rovkp.topic6.example5;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ShuffleReducer extends Reducer<IntWritable, Text, Text, NullWritable> {  
    
    @Override
    public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text value : values) {
            context.write(value, NullWritable.get());
        }
    }
}

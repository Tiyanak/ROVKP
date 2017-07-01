package hr.fer.tel.rovkp.topic6.example5;

import java.io.IOException;
import java.util.Random;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ShuffleMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
    
    private final Random random = new Random();
    
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();
            
            //try to parse to skip non-parsable
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

           context.write(new IntWritable(random.nextInt()), value);
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }      
}

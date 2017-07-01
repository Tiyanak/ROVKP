package hr.fer.tel.rovkp.topic6.example2;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DistinctMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();

            //try to parse to skip non-parsable
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

            context.write(value, NullWritable.get());
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }
}

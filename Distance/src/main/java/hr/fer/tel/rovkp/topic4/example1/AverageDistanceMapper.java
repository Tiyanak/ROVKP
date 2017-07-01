package hr.fer.tel.rovkp.topic4.example1;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author Ivana
 */
public class AverageDistanceMapper extends Mapper<LongWritable, Text, Text, CountAverageTuple> {

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        DEBSRecordParser parser = new DEBSRecordParser();

        //skip the first line
        if (key.get() > 0) {
            String record = value.toString();
            try {
                parser.parse(record);
                context.write(new Text(parser.getMedallion()), new CountAverageTuple(1, parser.getDistance()));
            } catch (Exception ex) {
                //System.out.println("Cannot parse: " + record + "due to the " + ex);
            }
        }
    }
}

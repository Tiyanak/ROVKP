package hr.fer.tel.rovkp.topic6.example6;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AverageDistanceMapper extends Mapper<LongWritable, Text, Text, CountAverageTuple> {

    private final CountAverageTuple out = new CountAverageTuple();

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

            out.setCount(1);
            out.setAverage(parsedRecord.getDistance());

            context.write(new Text(parsedRecord.getMedallion()), out);
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }
}

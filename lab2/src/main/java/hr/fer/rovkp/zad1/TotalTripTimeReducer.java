package hr.fer.rovkp.zad1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Igor Farszky on 3.4.2017..
 */
public class TotalTripTimeReducer extends Reducer<Text, TripTimeModel, Text, TripTimeModel>{

    private TripTimeModel result = new TripTimeModel();

    @Override
    public void reduce(Text key, Iterable<TripTimeModel> values, Context context) throws IOException, InterruptedException {

        int totalTripTime = 0;
        int minTripTime = Integer.MAX_VALUE;
        int maxTripTime = Integer.MIN_VALUE;

        for(TripTimeModel i: values){
            totalTripTime += i.getTotal().get();

            if(i.getMax().get() < minTripTime){
                minTripTime = i.getMin().get();
            }

            if(i.getMax().get() > maxTripTime){
                maxTripTime = i.getMax().get();
            }
        }

        result.set(new IntWritable(totalTripTime), new IntWritable(minTripTime), new IntWritable(maxTripTime));
        context.write(key, result);

    }

}

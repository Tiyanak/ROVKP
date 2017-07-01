package hr.fer.rovkp.zad1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Igor Farszky on 3.4.2017..
 */
public class TotalTripTimeMapper extends Mapper<LongWritable, Text, Text, TripTimeModel> {


    @Override
    public void map(LongWritable key, Text value, Context context){

        DEBSRecordParser debsRecordParser = new DEBSRecordParser();

        if(key.get() > 0){
            String record = value.toString();

            try {
                debsRecordParser.parse(record);
                context.write(new Text(debsRecordParser.getMedallion()), new TripTimeModel(debsRecordParser.getTrip_time(),
                        debsRecordParser.getTrip_time(), debsRecordParser.getTrip_time()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

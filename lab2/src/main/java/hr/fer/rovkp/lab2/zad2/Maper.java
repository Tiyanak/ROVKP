package hr.fer.rovkp.lab2.zad2;

import hr.fer.rovkp.lab2.DEBSFullRecord;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Igor Farszky on 11.4.2017..
 */
public class Maper extends Mapper<LongWritable, Text, IntWritable, Celija>{

    private static final double BEGIN_LON = 41.474937;
    private static final double BEGIN_LAT = -74.913585;
    private static final double GRID_WIDTH = 0.008983112;
    private static final double GRID_HEIGT = 0.011972;

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

            Celija c = new Celija(cellX(parsedRecord.getPickupLongitude()), cellY(parsedRecord.getPickupLatitude()),
                    1, parsedRecord.getTotal());

            context.write(new IntWritable(parsedRecord.getPickup().getHours()), c);
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }

    private int cellX(Double longi){

        return (int) ((longi - BEGIN_LON) / GRID_WIDTH) + 1;

    }

    private int cellY(Double lat){

        return (int) ((BEGIN_LAT - lat) / GRID_HEIGT) + 1;

    }

}

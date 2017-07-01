package hr.fer.rovkp.lab2.zad3;

import hr.fer.rovkp.lab2.DEBSFullRecord;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

/**
 * Created by Igor Farszky on 17.4.2017..
 */
public class FiltarMaper extends Mapper<LongWritable, Text, Text, NullWritable>{

    private static final double BEGIN_LON = 41.474937;
    private static final double BEGIN_LAT = -74.913585;
    private static final double GRID_WIDTH = 0.008983112;
    private static final double GRID_HEIGT = 0.011972;

    private MultipleOutputs<Text, NullWritable> mos = null;

    @Override
    protected void setup(Mapper.Context context) throws IOException, InterruptedException {
        mos = new MultipleOutputs<>(context);
    }

    @Override
    public void map(LongWritable key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

            String zadovoljiloString = zadovoljilo(parsedRecord);

            if(zadovoljiloString.equals("DA")) {
                //no condition, just categorize
                mos.write("bins", value, NullWritable.get(), zadovoljilo(parsedRecord));
            }
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }

    @Override
    protected void cleanup(Mapper.Context context) throws IOException, InterruptedException {
        mos.close();
    }

    private String zadovoljilo(DEBSFullRecord parsedRecord){

        if(parsedRecord.getTotal() <= 0){
            return "NE";
        }else{

            int pickX = cellX(parsedRecord.getPickupLongitude());
            int pickY = cellY(parsedRecord.getPickupLatitude());
            int dropX = cellX(parsedRecord.getDropoffLongitude());
            int dropY = cellY(parsedRecord.getDropoffLatitude());

            if(pickX > 150 || pickY > 150 || dropX > 150 || dropY > 150){
                return "NE";
            }else if(pickX != dropX){
                return "DA";
            }else if(pickY != dropY){
                return "DA";
            }else{
                return "NE";
            }

        }

    }

    private int cellX(Double longi){

        return (int) ((longi - BEGIN_LON) / GRID_WIDTH) + 1;

    }

    private int cellY(Double lat){

        return (int) ((BEGIN_LAT - lat) / GRID_HEIGT) + 1;

    }

}

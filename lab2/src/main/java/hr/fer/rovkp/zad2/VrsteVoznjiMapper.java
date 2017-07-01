package hr.fer.rovkp.zad2;

import hr.fer.rovkp.zad1.DEBSRecordParser;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Igor Farszky on 4.4.2017..
 */
public class VrsteVoznjiMapper extends Mapper<LongWritable, Text, Text, VrsteVoznjiModel>{

    VrsteVoznjiModel vvm = new VrsteVoznjiModel();

    @Override
    public void map(LongWritable key, Text value, Context context){

        if (key.get() > 0){
            String record = value.toString();
            try {
                DEBSRecordParser parser = new DEBSRecordParser();
                parser.parse(record);

                vvm.set(parser.getPickup_long(), parser.getPickup_lat(),
                        parser.getDrop_long(), parser.getDrop_lat(), parser.getPutnici());

                context.write(new Text(parser.getInput()), vvm);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

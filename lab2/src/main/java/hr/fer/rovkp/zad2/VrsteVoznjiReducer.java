package hr.fer.rovkp.zad2;

import hr.fer.rovkp.zad1.DEBSRecordParser;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Igor Farszky on 4.4.2017..
 */
public class VrsteVoznjiReducer extends Reducer<Text, VrsteVoznjiModel, Text, NullWritable> {

    @Override
    public void reduce(Text key, Iterable<VrsteVoznjiModel> values, Context context) throws IOException, InterruptedException {

        DEBSRecordParser parser = new DEBSRecordParser();

        context.write(key, NullWritable.get());

    }

}

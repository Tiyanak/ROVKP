package hr.fer.tel.rovkp.topic6.example3;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class BinningMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private MultipleOutputs<Text, NullWritable> mos = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        mos = new MultipleOutputs<>(context);
    }
            
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

            //no condition, just categorize
            mos.write("bins", value, NullWritable.get(), parsedRecord.getPaymentType());
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }        
}

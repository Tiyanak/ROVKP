package hr.fer.tel.rovkp.topic6.example6;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class BinningMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private MultipleOutputs<Text, DoubleWritable> mos = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        mos = new MultipleOutputs<>(context);
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        
        String medallion = value.toString().substring(0, 31);
        double avgDistance = Double.parseDouble(value.toString().substring(32));

        if (avgDistance < 2d) {
            mos.write("bins", medallion, avgDistance, "below2/part");
        } else {
            mos.write("bins", medallion, avgDistance, "above2/part");
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }
}

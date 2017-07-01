package hr.fer.tel.rovkp.topic6.example1;

import java.io.IOException;
import java.util.TreeMap;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TopkMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

    private TreeMap<DEBSFullRecord, String> topk;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        topk = new TreeMap<>(DEBSFullRecord.FAREHASH_COMPARATOR);
    }
        
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        try {
            String record = value.toString();
            DEBSFullRecord parsedRecord = new DEBSFullRecord(record);

            //keep best k records
            topk.put(parsedRecord, record);
            if (topk.size() > 30) {
                topk.pollFirstEntry();
            }
        } catch (Exception ex) {
            //System.out.println("Cannot parse: " + record + "due to the " + ex);
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        while (!topk.isEmpty()) {
            String record = topk.pollLastEntry().getValue();
            context.write(NullWritable.get(), new Text(record));
        }
    }
}

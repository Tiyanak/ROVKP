package hr.fer.tel.rovkp.topic6.example1;

import java.io.IOException;
import java.util.TreeMap;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TopkReducer extends Reducer<NullWritable, Text, NullWritable, Text> {

    private TreeMap<DEBSFullRecord, String> topk;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        topk = new TreeMap<>(DEBSFullRecord.FAREHASH_COMPARATOR);
    }   
    
    @Override
    public void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text value : values) {
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

        while (!topk.isEmpty()) {
            String record = topk.pollLastEntry().getValue();
            context.write(NullWritable.get(), new Text(record));
        }
    }
}

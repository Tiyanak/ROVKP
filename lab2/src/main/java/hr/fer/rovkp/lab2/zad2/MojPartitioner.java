package hr.fer.rovkp.lab2.zad2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by Igor Farszky on 12.4.2017..
 */
public class MojPartitioner extends Partitioner<IntWritable, Celija>{

    @Override
    public int getPartition(IntWritable keySat, Celija value, int i) {

        return keySat.get();

    }
}

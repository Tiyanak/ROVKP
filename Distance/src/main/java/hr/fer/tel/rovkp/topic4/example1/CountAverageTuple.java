package hr.fer.tel.rovkp.topic4.example1;

import java.io.*;
import org.apache.hadoop.io.*;

/**
 *
 * @author Ivana
 */
public class CountAverageTuple
        implements WritableComparable<CountAverageTuple> {

    private IntWritable first;
    private DoubleWritable second;

    public CountAverageTuple() {
        set(new IntWritable(), new DoubleWritable());
    }

    public CountAverageTuple(int first, double second) {
        set(first, second);
    }

    public CountAverageTuple(IntWritable first, DoubleWritable second) {
        set(first, second);
    }

    public void set(IntWritable first, DoubleWritable second) {
        this.first = first;
        this.second = second;
    }

    public void set(int first, double second) {
        this.first = new IntWritable(first);
        this.second = new DoubleWritable(second);
    }

    public int getCount() {
        return first.get();
    }

    public double getAverage() {
        return second.get();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        first.write(out);
        second.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        first.readFields(in);
        second.readFields(in);
    }

    @Override
    public int hashCode() {
        return first.hashCode() * 163 + second.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CountAverageTuple) {
            CountAverageTuple tp = (CountAverageTuple) o;
            return first.equals(tp.first) && second.equals(tp.second);
        }
        return false;
    }

    @Override
    public String toString() {
        return first.toString() + "\t" + second.toString();
    }

    @Override
    public int compareTo(CountAverageTuple tp) {
        int cmp = first.compareTo(tp.first);
        if (cmp != 0) {
            return cmp;
        }
        return second.compareTo(tp.second);
    }
}

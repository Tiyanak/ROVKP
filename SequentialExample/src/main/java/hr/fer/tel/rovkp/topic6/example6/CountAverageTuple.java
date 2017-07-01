package hr.fer.tel.rovkp.topic6.example6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class CountAverageTuple implements Writable {

    private long count = 0;
    private double average = 0;

    public double getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        count = in.readLong();
        average = in.readFloat();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(count);
        out.writeDouble(average);
    }

    @Override
    public String toString() {
        return Long.toString(count) + "\t" + Double.toString(average);
    }
}

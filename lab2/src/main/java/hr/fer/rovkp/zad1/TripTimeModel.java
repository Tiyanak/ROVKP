package hr.fer.rovkp.zad1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Igor Farszky on 4.4.2017..
 */
public class TripTimeModel implements WritableComparable<TripTimeModel>{

    private IntWritable total;
    private IntWritable min;
    private IntWritable max;

    public TripTimeModel(){
        set(new IntWritable(), new IntWritable(), new IntWritable());
    }

    public TripTimeModel(int total, int min, int max){
        this.total = new IntWritable(total);
        this.min = new IntWritable(min);
        this.max = new IntWritable(max);
    }

    public void set(IntWritable total, IntWritable min, IntWritable max) {
        this.total = total;
        this.min = min;
        this.max = max;
    }



    public IntWritable getTotal() {
        return total;
    }

    public IntWritable getMin() {
        return min;
    }

    public IntWritable getMax() {
        return max;
    }

    @Override
    public int compareTo(TripTimeModel o) {
        int cmp1 = total.compareTo(o.getTotal());
        if(cmp1 != 0){
            return cmp1;
        }else{
            int cmp2 = max.compareTo(o.max);
            if(cmp2 != 0){
                return cmp2;
            }else{
                return min.compareTo(o.min);
            }
        }

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        total.write(dataOutput);
        min.write(dataOutput);
        max.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        total.readFields(dataInput);
        min.readFields(dataInput);
        max.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripTimeModel that = (TripTimeModel) o;

        if (!total.equals(that.total)) return false;
        if (!min.equals(that.min)) return false;
        return max.equals(that.max);

    }

    @Override
    public int hashCode() {
        int result = total.hashCode();
        result = 31 * result + min.hashCode();
        result = 31 * result + max.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TripTimeModel{" +
                "total=" + total +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}

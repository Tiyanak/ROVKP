package hr.fer.rovkp.zad2;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Igor Farszky on 4.4.2017..
 */
public class VrsteVoznjiModel implements WritableComparable<VrsteVoznjiModel> {

    private DoubleWritable pickup_long;
    private DoubleWritable pickup_lat;
    private DoubleWritable drop_long;
    private DoubleWritable drop_lat;
    private IntWritable putnici;

    public VrsteVoznjiModel() {
    }

    public VrsteVoznjiModel(Double pickup_long, Double pickup_lat, Double drop_long,
                            Double drop_lat, Integer putnici){
       set(pickup_long, pickup_lat, drop_long, drop_lat, putnici);
    }

    public void set(DoubleWritable pickup_long, DoubleWritable pickup_lat, DoubleWritable drop_long,
                    DoubleWritable drop_lat, IntWritable putnici){
        this.pickup_long = pickup_long;
        this.pickup_lat = pickup_lat;
        this.drop_long = drop_long;
        this.drop_lat = drop_lat;
        this.putnici = putnici;
    }

    public void set(Double pickup_long, Double pickup_lat, Double drop_long,
                    Double drop_lat, Integer putnici){
        this.pickup_long = new DoubleWritable(pickup_long);
        this.pickup_lat = new DoubleWritable(pickup_lat);
        this.drop_long = new DoubleWritable(drop_long);
        this.drop_lat = new DoubleWritable(drop_lat);
        this.putnici = new IntWritable(putnici);
    }

    @Override
    public int compareTo(VrsteVoznjiModel o) {
        int cmp1 = pickup_long.compareTo(o.pickup_long);

        if(cmp1 != 0){
            return cmp1;
        }else{
            int cmp2 = pickup_lat.compareTo(o.pickup_lat);

            if(cmp2 != 0){
                return cmp2;
            }else{
                int cmp3 = drop_long.compareTo(o.drop_long);

                if(cmp3 != 0){
                    return cmp3;
                }else{
                    return drop_lat.compareTo(o.drop_lat);
                }
            }
        }
    }

    public DoubleWritable getPickup_long() {
        return pickup_long;
    }

    public void setPickup_long(DoubleWritable pickup_long) {
        this.pickup_long = pickup_long;
    }

    public DoubleWritable getPickup_lat() {
        return pickup_lat;
    }

    public void setPickup_lat(DoubleWritable pickup_lat) {
        this.pickup_lat = pickup_lat;
    }

    public DoubleWritable getDrop_long() {
        return drop_long;
    }

    public void setDrop_long(DoubleWritable drop_long) {
        this.drop_long = drop_long;
    }

    public DoubleWritable getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(DoubleWritable drop_lat) {
        this.drop_lat = drop_lat;
    }

    public IntWritable getPutnici() {
        return putnici;
    }

    public void setPutnici(IntWritable putnici) {
        this.putnici = putnici;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        pickup_long.write(dataOutput);
        pickup_lat.write(dataOutput);
        drop_long.write(dataOutput);
        drop_lat.write(dataOutput);
        putnici.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        pickup_long.readFields(dataInput);
        pickup_lat.readFields(dataInput);
        drop_long.readFields(dataInput);
        drop_lat.readFields(dataInput);
        putnici.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VrsteVoznjiModel that = (VrsteVoznjiModel) o;

        if (!pickup_long.equals(that.pickup_long)) return false;
        if (!pickup_lat.equals(that.pickup_lat)) return false;
        if (!drop_long.equals(that.drop_long)) return false;
        if (!drop_lat.equals(that.drop_lat)) return false;
        return putnici.equals(that.putnici);

    }

    @Override
    public int hashCode() {
        int result = pickup_long.hashCode();
        result = 31 * result + pickup_lat.hashCode();
        result = 31 * result + drop_long.hashCode();
        result = 31 * result + drop_lat.hashCode();
        result = 31 * result + putnici.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VrsteVoznjiModel{" +
                "pickup_long=" + pickup_long +
                ", pickup_lat=" + pickup_lat +
                ", drop_long=" + drop_long +
                ", drop_lat=" + drop_lat +
                ", putnici=" + putnici +
                '}';
    }
}

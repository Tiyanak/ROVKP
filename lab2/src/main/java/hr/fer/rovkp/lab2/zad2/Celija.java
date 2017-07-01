package hr.fer.rovkp.lab2.zad2;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Igor Farszky on 12.4.2017..
 */
public class Celija implements WritableComparable<Celija>{

    private IntWritable x;
    private IntWritable y;
    private IntWritable broj;
    private DoubleWritable prihod;

    public Celija() {
    }

    public Celija(IntWritable x, IntWritable y, IntWritable broj, DoubleWritable prihod) {
        this.x = x;
        this.y = y;
        this.broj = broj;
        this.prihod = prihod;
    }

    public Celija(int x, int y, int broj, double prihod){
        this.x = new IntWritable(x);
        this.y = new IntWritable(y);
        this.broj = new IntWritable(broj);
        this.prihod = new DoubleWritable(prihod);
    }

    public IntWritable getX() {
        return x;
    }

    public void setX(IntWritable x) {
        this.x = x;
    }

    public IntWritable getY() {
        return y;
    }

    public void setY(IntWritable y) {
        this.y = y;
    }

    public IntWritable getBroj() {
        return broj;
    }

    public void setBroj(IntWritable broj) {
        this.broj = broj;
    }

    public DoubleWritable getPrihod() {
        return prihod;
    }

    public void setPrihod(DoubleWritable prihod) {
        this.prihod = prihod;
    }

    @Override
    public int compareTo(Celija o) {
        int xpos = x.compareTo(o.getX());

        if(xpos == 0){

            int ypos = y.compareTo(o.getY());

            if(ypos == 0){

                return prihod.compareTo(o.getPrihod());

            }else{
                return ypos;
            }

        }else{
            return xpos;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.x.write(dataOutput);
        this.y.write(dataOutput);
        this.broj.write(dataOutput);
        this.prihod.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.x.readFields(dataInput);
        this.y.readFields(dataInput);
        this.broj.readFields(dataInput);
        this.prihod.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Celija celija = (Celija) o;

        if (x != null ? !x.equals(celija.x) : celija.x != null) return false;
        return y != null ? y.equals(celija.y) : celija.y == null;

    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}

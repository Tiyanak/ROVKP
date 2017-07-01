package hr.fer.rovkp.lab2.zad2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Igor Farszky on 11.4.2017..
 */
public class MojReducer extends Reducer<IntWritable, Celija, Text, NullWritable> {

    String sat;
    Map<Celija, Integer> voznje;
    Map<Celija, Double> prihodi;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        voznje = new HashMap<>();
        prihodi = new HashMap<>();
        sat = "";
    }

    @Override
    public void reduce(IntWritable key, Iterable<Celija> values, Context context) throws IOException, InterruptedException {

        sat = String.valueOf(key.get());

        for (Celija value : values) {

            if(!voznje.containsKey(value)){
                voznje.put(value, 1);
            }else{
                voznje.put(value, voznje.get(value)+1);
            }

            if(!prihodi.containsKey(value)){
                prihodi.put(value, value.getPrihod().get());
            }else{
                prihodi.put(value, prihodi.get(value) + value.getPrihod().get());
            }

        }

        int maxVoznji = 0;
        double maxPrihod = 0;

        Celija cVoznjeMax = new Celija();
        Celija cPrihodiMax = new Celija();

        for(Map.Entry<Celija, Integer> entry: voznje.entrySet()){
            if(entry.getValue() > maxVoznji){
                maxVoznji = entry.getValue();
                cVoznjeMax = entry.getKey();
            }
        }

        for(Map.Entry<Celija, Double> entry: prihodi.entrySet()){
            if(entry.getValue() > maxPrihod){
                maxPrihod = entry.getValue();
                cPrihodiMax = entry.getKey();
            }
        }

        String maxVoznjeStirng = "" + String.valueOf(cVoznjeMax.getX().get()) + ", " +
                String.valueOf(cVoznjeMax.getY().get()) + " : " + maxVoznji;
        String maxPrihodString = "" + String.valueOf(cPrihodiMax.getX().get()) + ", " +
                String.valueOf(cVoznjeMax.getY().get()) + " : " + maxPrihod;

        context.write(new Text(sat), NullWritable.get());
        context.write(new Text(maxVoznjeStirng), NullWritable.get());
        context.write(new Text(maxPrihodString), NullWritable.get());

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);

        voznje.clear();
        prihodi.clear();
        sat = "";

    }

}
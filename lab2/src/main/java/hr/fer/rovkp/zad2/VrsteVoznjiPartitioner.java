package hr.fer.rovkp.zad2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by Igor Farszky on 4.4.2017..
 */
public class VrsteVoznjiPartitioner extends Partitioner<Text, VrsteVoznjiModel>{

    private double long1 = -74;
    private double lat1 = 40.8;
    private double long2 = -73.95;
    private double lat2 = 40.75;

    @Override
    public int getPartition(Text key, VrsteVoznjiModel value, int i) {

        int fileNumber = 0;

        if(centarGrada(value)){

            if(value.getPutnici().get() == 1){
                fileNumber = 0;
            }else if(value.getPutnici().get() > 3){
                fileNumber = 2;
            }else{
                fileNumber = 1;
            }

        }else{

            if(value.getPutnici().get() == 1){
                fileNumber = 3;
            }else if(value.getPutnici().get() > 3){
                fileNumber = 5;
            }else{
                fileNumber = 4;
            }

        }

        return fileNumber;

    }

    private boolean centarGrada(VrsteVoznjiModel value){

        if(uOpsegu(value.getPickup_long().get(), value.getPickup_lat().get()) &&
                uOpsegu(value.getDrop_long().get(), value.getDrop_lat().get())){
            return true;
        }else{
            return false;
        }

    }

    private boolean uOpsegu(double pick_long, double pick_lat){

        if(pick_long < long1 || pick_long > long2){
            return false;
        }else{
            if (pick_lat > lat1 || pick_lat < lat2){
                return false;
            }else{
                return true;
            }
        }

    }
}

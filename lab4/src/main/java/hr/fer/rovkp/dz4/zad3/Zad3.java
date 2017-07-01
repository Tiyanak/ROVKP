package hr.fer.rovkp.dz4.zad3;

import hr.fer.rovkp.dz4.zad1.SensorscopeReading;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.NoSuchElementException;

/**
 * Created by Igor Farszky on 30.5.2017..
 */
public class Zad3 {

    public static void main(String[] args){

        SparkConf conf = new SparkConf().setAppName("SparkStreaming");


        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            //spark streaming application requires at least 2 threads
            conf.setMaster("local[2]");
        }

        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));

        //crate a stream from text records
        JavaDStream<String> records = jssc.socketTextStream("localhost", 10002);

        // # 1. filtriraj sve kaj se nemre parsirat
        // # 2. filt u tok ocitanja sensorscopereading
        // # 3. u tok parova stationid solar current
        // 4. za svaki stationid naci max current u windowu 60 sec koji se izrac svakih 10 sec
        // 5. neka max budu uparovima
        // 6. pohrani na disk

        JavaPairDStream<Integer, Double> rdd = records.filter(d -> SensorscopeReading.isParsable(d))
                .map(SensorscopeReading::new)
                .mapToPair(d -> new Tuple2<>(d.getStationID(), d.getSolarCurrent()))
                .reduceByKeyAndWindow(Math::max, Durations.seconds(60), Durations.seconds(10));

        rdd.dstream().saveAsTextFiles(args[0], "txt");
        jssc.start();
        jssc.awaitTermination();

    }

}

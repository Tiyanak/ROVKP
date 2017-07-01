package hr.fer.rovkp.lab4.zad3;

import hr.fer.rovkp.dz4.zad1.SensorscopeReading;
import hr.fer.rovkp.lab4.zad1.Pollution;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.NoSuchElementException;

/**
 * Created by Igor Farszky on 5.6.2017..
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
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(3));
        JavaDStream<String> records = jssc.socketTextStream("localhost", 10002);

        JavaPairDStream<Station, Integer> rdd = records.filter(Pollution::isParsable)
                .map(Pollution::new)
                .mapToPair(d -> new Tuple2<>(new Station(d.getLongitude(), d.getLatitude()), d.getOzone()))
                .reduceByKeyAndWindow(Math::min, Durations.seconds(45), Durations.seconds(15));

        rdd.dstream().saveAsTextFiles(args[0], "txt");
        jssc.start();
        jssc.awaitTermination();

    }

}
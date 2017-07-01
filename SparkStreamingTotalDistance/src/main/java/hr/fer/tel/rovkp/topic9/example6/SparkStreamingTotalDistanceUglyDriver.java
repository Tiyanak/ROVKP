package hr.fer.tel.rovkp.topic9.example6;

import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class SparkStreamingTotalDistanceUglyDriver {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("SparkStreamingTotalDistanceDriver");

        //set the master if not already set through the command line
        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            //spark streaming application requires at least 2 threads
            conf.setMaster("local[2]");
        }

        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(15));

        //crate a stream from text records
        JavaDStream<String> records = jssc.socketTextStream("localhost", 18181);

        //filter records from the stream
        JavaDStream<String> filteredRecords = records.filter(record -> DEBSFullRecord.isParsable(record));        

        //create a new stream with ID-distance tuples 
        JavaPairDStream<String, Double> pairs = filteredRecords.mapToPair(line -> new Tuple2<>(line.split(",")[0],
                Double.parseDouble(line.split(",")[5])));

        //aggregate tuples by ID and sum their distance
        JavaPairDStream<String, Double> result = pairs.reduceByKey((x, y) -> x + y);

         //save aggregated tuples to text file
        result.dstream().saveAsTextFiles(args[0],"txt");

        //start our streaming context and wait for it to "finish"
        jssc.start();

        //wait for the job to finish
        jssc.awaitTermination();
    }
}

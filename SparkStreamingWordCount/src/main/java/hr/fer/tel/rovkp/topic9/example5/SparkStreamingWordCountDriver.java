package hr.fer.tel.rovkp.topic9.example5;

import java.util.Arrays;
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
public class SparkStreamingWordCountDriver {

    public static void main(String[] args) {
        
        if (args.length != 1) {
            System.err.println("Usage: SparkStreamingWordCountDriver <output dir>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("SparkStreamingWordCountDriver");

        //set the master if not already set through the command line
        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            //spark streaming application requires at least 2 threads
            conf.setMaster("local[2]");
        }

        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));

        //crate a stream from text lines
        JavaDStream<String> lines = jssc.socketTextStream("localhost", 18181);

        //do the job
        JavaPairDStream<String, Long> result = lines.
                flatMap(line -> Arrays.asList(line.trim().split("\\s"))).
                map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim()).
                filter(word -> word.length() > 0).
                mapToPair(word -> new Tuple2<>(word, 1L)).
                reduceByKey((x, y) -> x + y);

        //save aggregated tuples to text file
        result.dstream().saveAsTextFiles(args[0], "txt");

        //start our streaming context and wait for it to "finish"
        jssc.start();

        //wait for the job to finish
        jssc.awaitTermination();
    }
}

package hr.fer.tel.rovkp.topic9.example4;

import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class TotalDistanceDriver {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: TotalDistanceDriver <input file> <output dir>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("TotalDistance");

        //set the master if not already set through the command line
        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            conf.setMaster("local");
        }

        JavaSparkContext sc = new JavaSparkContext(conf);

         //crate an RDD from text file records
        JavaRDD<String> records = sc.textFile(args[0]);

        //do the job
        JavaPairRDD<String, Double> result = records.
                filter(line -> DEBSFullRecord.isParsable(line)).
                mapToPair(line -> new Tuple2<>(line.split(",")[0], 
                        Double.parseDouble(line.split(",")[5]))).
                reduceByKey((x, y) -> x + y);

        //write result to a file
        result.saveAsTextFile(args[1]);

    }
}

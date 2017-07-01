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
public class TotalDistanceUglyDriver {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: TotalDistanceUglyDriver <input file> <output dir>");
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
        
        //filter records from the RDD
        JavaRDD<String> parsableRecords = records.filter(line -> DEBSFullRecord.isParsable(line));
        
        //create a new RDD with ID-distance tuples       
        JavaPairRDD<String, Double> pairs = parsableRecords.mapToPair(line -> new Tuple2<String, Double>(line.split(",")[0],
                Double.parseDouble(line.split(",")[5])));
               
        //aggregate tuples by ID and sum their distance
        JavaPairRDD<String, Double> result = pairs.reduceByKey((x, y) -> x + y);
        
        //write aggregated tuples to a file
        result.saveAsTextFile(args[1]);

    }
}

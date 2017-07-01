package hr.fer.tel.rovkp.topic9.example3;

import java.util.Arrays;
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
public class WordCountUglyDriver {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: WordCountUglyDriver <input file> <output dir>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("WordCount");

        //set the master if not already set through the command line
        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            conf.setMaster("local");
        }

        JavaSparkContext sc = new JavaSparkContext(conf);

        //crate an RDD from text file lines
        JavaRDD<String> lines = sc.textFile(args[0]);

        //get an RDD of words
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.trim().split("\\s")));

        //filter non-letters from the RDD of words
        JavaRDD<String> alphaWords = words.map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim());
        JavaRDD<String> filteredWords = alphaWords.filter(word -> word.length() > 0);

        //create a new RDD with word-count tuples       
        JavaPairRDD<String, Long> pairs = filteredWords.mapToPair(word -> new Tuple2<>(word, 1L));

        //aggregate tuples by words and sum their counts                
        JavaPairRDD<String, Long> result = pairs.reduceByKey((x, y) -> x + y);

        //write aggregated tuples to a file
        result.saveAsTextFile(args[1]);

    }
}

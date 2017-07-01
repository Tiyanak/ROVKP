/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic12.example1;

import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.GraphLoader;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class GraphxDegreesDriver {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("GraphFramesDegrees");

        //set the master if not already set through the command line
        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            conf.setMaster("local");
        }

        JavaSparkContext jsc = new JavaSparkContext(conf);

        //load graph from disk
        Graph graph = GraphLoader.edgeListFile(jsc.sc(),
                "/home/kpripuzic/Magnetic/3980.edges", false, -1,
                StorageLevel.MEMORY_ONLY(), StorageLevel.MEMORY_ONLY());

        //get vertex degrees as an RDD
        JavaRDD<Tuple2<Integer, Integer>> vertexDegrees = graph.ops().outDegrees().toJavaRDD();
        
        //print vertices
        System.out.println("Vertices:");
        vertexDegrees.foreach(v -> System.out.println(v));

        //create a new RDD with degree-one tuples  
        JavaPairRDD<Integer, Integer> degreeCounts = vertexDegrees.
                mapToPair(pair -> new Tuple2<Integer, Integer>(pair._2, 1));

        //get max vertex degree
        int maxDegree = degreeCounts.
                sortByKey(false).
                first()._1;

        //print vertex degree counts
        Map<Integer, Integer> degreeCountsMap = degreeCounts.
                reduceByKey((x, y) -> x + y).
                collectAsMap();

        System.out.println("Degree distribution: ");
        for (int degree = 1; degree <= maxDegree; degree++) {
            Integer counts = degreeCountsMap.get(degree);
            if (counts == null) {
                System.out.println(0);
            } else {
                System.out.println(counts);
            }
        }
    }
}

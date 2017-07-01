/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic12.example4;

import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.GraphLoader;
import org.apache.spark.graphx.lib.PageRank;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class GraphxPageRankDriver {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("GraphxPageRankDriver");

        //set the master if not already set through the command line
        try {
            conf.get("spark.master");
        } catch (NoSuchElementException ex) {
            conf.setMaster("local");
        }

        JavaSparkContext jsc = new JavaSparkContext(conf);

        //load graph from file
        Graph graph = GraphLoader.edgeListFile(jsc.sc(),
                "/home/kpripuzic/Magnetic/3980.edges", false, -1,
                StorageLevel.MEMORY_ONLY(), StorageLevel.MEMORY_ONLY());

        //get pr graph
        Graph pgGraph = PageRank.runUntilConvergence(graph, 0.01d, 0.85d, null, null);

        //get pr vertices
        JavaRDD<Tuple2<Long, Double>> prVertices = pgGraph.vertices().<Tuple2<Long, Double>>toJavaRDD();
        
        //print pr vertices        
        System.out.println("PR Vertices:");
        prVertices.foreach(v -> System.out.println(v));
        
        JavaPairRDD<Double, Integer> prCounts = prVertices.
                mapToPair(pair -> new Tuple2<Double, Integer>(pair._2, 1)).
                reduceByKey((x, y) -> x + y).sortByKey();

        prCounts.foreach(prc -> System.out.println(prc._1 + " " + prc._2));
    }
}

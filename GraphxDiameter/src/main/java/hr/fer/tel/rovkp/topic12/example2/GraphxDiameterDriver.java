/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic12.example2;

import akka.japi.Util;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.GraphLoader;
import org.apache.spark.graphx.lib.ShortestPaths;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.collection.immutable.Map;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class GraphxDiameterDriver {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("GraphxDiameterDriver");

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

        //print vertices
        JavaRDD<Tuple2<Long, Integer>> vertices = graph.vertices().toJavaRDD();
        System.out.println("Vertices:");
        vertices.foreach(v -> System.out.println(v));

        //get shortest paths graph
        List<Long> vertexIds = vertices.map(pair -> pair._1).collect();
        Seq seq = Util.immutableSeq(vertexIds);
        Graph spGraph = ShortestPaths.run(graph, seq, null);

        //get shortest path vertices
        JavaRDD<Tuple2<Integer, Map<Integer, Integer>>> spVertices
                = spGraph.vertices().toJavaRDD();

        //print sp vertices
        System.out.println("SP Vertices:");
        spVertices.foreach(spv -> System.out.println(spv));

        //get an RDD of shortest path lengths
        JavaDoubleRDD spls = spVertices.
                flatMap(spv -> getJavaList(spv._2.values())).
                filter(spl -> spl > 0).
                mapToDouble(spl -> (double) spl);

        //print diameter and average shortest path
        double diameter = spls.max();
        System.out.println("Diameter: " + diameter);

        double asp = spls.mean();
        System.out.println("Average shortest path: " + asp);
    }

    private static <T> List<T> getJavaList(scala.collection.Iterable<T> iterable) {
        return new LinkedList<>(JavaConverters.asJavaCollectionConverter(iterable).asJavaCollection());
    }
}

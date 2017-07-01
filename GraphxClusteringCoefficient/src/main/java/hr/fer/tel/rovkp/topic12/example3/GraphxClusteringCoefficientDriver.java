/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic12.example3;

import cn.edu.tsinghua.keg.spark.graphx.lib.LocalClusteringCoefficient;
import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
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
public class GraphxClusteringCoefficientDriver {
    
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("GraphxClusteringCoefficientDriver");

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

        //get lcc graph
        Graph<Object, Object> lccGraph = LocalClusteringCoefficient.run(graph, null, null);

        //get lcc vertices
        JavaRDD<Tuple2<Object, Object>> lccVertices = lccGraph.vertices().toJavaRDD();
        
        //print vertices
        System.out.println("LCC Vertices:");
        lccVertices.foreach(v -> System.out.println(v));
        
        double nacc = lccVertices.
                mapToDouble(lccv -> (double) lccv._2).
                mean();
        
        System.out.println("Network average clustering coefficeint: " + nacc);
    }
}

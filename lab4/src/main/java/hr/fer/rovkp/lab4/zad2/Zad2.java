package hr.fer.rovkp.lab4.zad2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Igor Farszky on 5.6.2017..
 */
public class Zad2 {

    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf().setAppName("Newborns");

        try {
            sparkConf.get("spark.master");
        } catch (NoSuchElementException e) {
            sparkConf.setMaster("local");
        }

        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> records = sc.textFile(args[0]);
        JavaRDD<DeathRecord> rdd = records.filter(DeathRecord::isParsable).map(DeathRecord::new).cache();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("/home/rovkp/results.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.write("\nZena umrlo u lipnju: " + rdd.filter(d -> d.getSex().equals("F") && d.getMonthOfDeath() == 6).count());

            Map<Integer, Object> dayOfWeek = rdd.filter(d -> d.getAge() > 50).mapToPair(d -> new Tuple2<>(d.getDayOfWeekDeath(), 1)).countByKey();
            Long max = new Long(0);
            Integer sol = -1;
            for (Map.Entry kv : dayOfWeek.entrySet()){
                if ((Long)kv.getValue() > max){
                    max = (Long) kv.getValue();
                    sol = (Integer) kv.getKey();
                }
            }
            writer.write("\nDan u tjednu sa najvise muskih umrlih starijih od 50: " + sol + ", "+ max + " ljudi");

            writer.write("\nBroj osoba na obdukciji: " + rdd.filter(d -> d.getAutopsy().equals("Y")).count());

            writer.write("\nKretanje broja umrlih muskih izmedu 45-65 po mjesecima sortirano (mjesec, broj umrlih): ");
            JavaRDD<DeathRecord> rddOlds = rdd.filter(x -> 45 <= x.getAge() && x.getAge() < 65 && x.getSex().equals("M")).cache();
            Map<Integer, Object> md = rddOlds.mapToPair(x -> new Tuple2<>(x.getMonthOfDeath(), 1)).countByKey();
            for (int i = 1; i <=12; ++i) {
                writer.write("\n" + i + " " + md.get(i));
            }

            writer.write("\nKretanje postotka umrlih ozenjenih muskih 45-65 po mjesecima (mjesec, postotak): ");
            JavaRDD<DeathRecord> rddOldMar = rddOlds.filter(x -> x.getMaritalStatus().equals("M")).cache();
            Long size = rddOldMar.count();
            Map<Integer, Double> md2 = rddOldMar.mapToPair(x -> new Tuple2<>(x.getMonthOfDeath(),1.0/size)).reduceByKey((x,y) -> x + y).collectAsMap();

            for (int i = 1; i <=12; ++i) {
                writer.write("\n" + i + " " + md2.get(i));
            }

            writer.write("\nUkupno umrlih u nesreci: " + rdd.filter(x->x.getMannerOfDeath() == 1).count());
            writer.write("\nBroj razlicitih godina starosti: " + rdd.map(DeathRecord::getAge).distinct().count());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

package hr.fer.tel.rovkp.topic9.example2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import java.util.stream.Stream;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class TotalDistanceMain {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: TotalDistanceMain <input file> <output file>");
            System.exit(-1);
        }

        //read records into the stream
        try (Stream<String> records = Files.lines(Paths.get(args[0]))) {

            //do the job
            Map<String, Double> result = records.
                    filter(line -> DEBSFullRecord.isParsable(line)).
                    map(line -> new SimpleEntry<>(line.split(",")[0], Double.parseDouble(line.split(",")[5]))).
                    collect(groupingBy(SimpleEntry::getKey, summingDouble(SimpleEntry::getValue)));

            //write results to a file
            FileWriter fw = new FileWriter(args[1]);
            result.forEach((k, v) -> writeToFile(fw, k + "," + v + "\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(FileWriter fw, String line) {
        try {
            fw.write(line);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

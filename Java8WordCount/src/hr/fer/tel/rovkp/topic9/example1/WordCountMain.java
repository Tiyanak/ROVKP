package hr.fer.tel.rovkp.topic9.example1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import java.util.stream.Stream;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class WordCountMain {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: WordCountMain <input file> <output file>");
            System.exit(-1);
        }

        //read lines into the stream
        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {

            //do the job
            Map<String, Long> result = lines.
                    flatMap(line -> Arrays.stream(line.trim().split("\\s"))).
                    map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim()).
                    filter(word -> word.length() > 0).
                    map(word -> new SimpleEntry<>(word, 1L)).
                    collect(groupingBy(SimpleEntry::getKey, counting()));

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

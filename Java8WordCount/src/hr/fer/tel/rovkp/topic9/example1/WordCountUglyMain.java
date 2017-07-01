package hr.fer.tel.rovkp.topic9.example1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class WordCountUglyMain {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: WordCountUglyMain <input file> <output file>");
            System.exit(-1);
        }

        //read lines into the stream
        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {

            //get a stream of words
            Stream<String> words = lines.flatMap(line -> Arrays.stream(line.trim().split("\\s")));
            
            //filter non-letters from the stream of words
            Stream<String> alphaWords = words.map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim());
            Stream<String> filteredWords = alphaWords.filter(word -> word.length() > 0);

            //create a stream of word-count entries
            Stream<SimpleEntry<String, Long>> entries = filteredWords.map(word -> new SimpleEntry<>(word, 1L));

            //aggragate entries by words and sum their counts
            Map<String, Long> result = entries.collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.counting()));

            //write aggregated entries to a file
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

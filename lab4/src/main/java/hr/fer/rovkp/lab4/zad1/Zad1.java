package hr.fer.rovkp.lab4.zad1;

import hr.fer.rovkp.dz4.zad1.SensorscopeReading;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Igor Farszky on 5.6.2017..
 */
public class Zad1 {

    private static String polucijaPath = "C:\\Users\\Igor Farszky\\Desktop\\rovkp-data\\polucija";
    private static String polucijaWritePath = "C:\\Users\\Igor Farszky\\Desktop\\rovkp-data\\pollution-all.csv.";

    public static void main(String[] args) throws IOException {

        File f = new File(polucijaPath);
        FilenameFilter textFilter = (dir, name) -> name.endsWith(".csv");
        File[] files = f.listFiles(textFilter);
        Stream<String> total_lines = Stream.empty();

        for (int i=0; i<files.length; i++){
            Stream<String> lines1 = Files.lines(Paths.get(files[i].getCanonicalPath()));
            total_lines = Stream.concat(total_lines, lines1);
        }

        FileWriter fw = new FileWriter(polucijaWritePath);
        total_lines.filter(Pollution::isParsable).
                map(Pollution::new).
                sorted(Pollution.TIME_COMP).
                forEach(pollution -> writeToFile(fw, pollution.toString() + "\n"));
    }

    private static void writeToFile(FileWriter fw, String line) {
        try {
            fw.write(line);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

package hr.fer.rovkp.dz4.zad1;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Igor Farszky on 29.5.2017..
 */
public class DZ4 {

    private static String senzorPath = "C:\\Users\\Igor Farszky\\Desktop\\senzor\\";
    private static String senzorWritePath = "C:\\Users\\Igor Farszky\\Desktop\\senzor\\senesorscope-monitor-all.csv.";

    public static void main(String[] args) throws IOException {

        File f = new File(senzorPath);

        FilenameFilter textFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt") ? true : false;
            }
        };

        File[] files = f.listFiles(textFilter);

        Stream<String> total_lines = Stream.empty();
        for (int i=0; i<files.length; i++){
            Stream<String> lines1 = Files.lines(Paths.get(files[i].getCanonicalPath()));

            total_lines = Stream.concat(total_lines, lines1);
        }

        FileWriter fw = new FileWriter(senzorWritePath);
        total_lines.filter(line -> SensorscopeReading.isParsable(line)).
                map(line -> new SensorscopeReading(line)).
                sorted(SensorscopeReading.TIME_COMP).
                forEach(senzor -> writeToFile(fw, senzor.toString()));
    }

    private static void writeToFile(FileWriter fw, String line) {
        try {
            fw.write(line);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

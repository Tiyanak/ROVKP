package hr.fer.rovkp.dz3;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Igor Farszky on 9.5.2017..
 */
public class JesterParser {

    private File file;

    public JesterParser(File file) {
        this.file = file;
    }

    public Map<Integer, String> getDataMap() throws IOException {

        Map<Integer, String> dataMap = new TreeMap<Integer, String>();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = "";
        Integer jokeId = 0;
        String jokeText = "";
        while( (line = br.readLine()) != null){

            if(line.matches("^[0-9]+:$")) {
                jokeId = Integer.parseInt(line.replaceAll(":", ""));
            }else if(line.matches("^$")){
                if(jokeText.endsWith("\n")){
                    jokeText = jokeText.substring(0, jokeText.length()-1);
                }
                dataMap.put(jokeId, jokeText);
                jokeText = "";
            }else{
                String concatString = StringEscapeUtils.unescapeXml(line.toLowerCase().replaceAll("\\<.*?\\>", ""));
                if(!concatString.isEmpty()) {
                    jokeText = jokeText.concat(concatString.concat("\n"));
                }
            }

        }

        return dataMap;

    }

}

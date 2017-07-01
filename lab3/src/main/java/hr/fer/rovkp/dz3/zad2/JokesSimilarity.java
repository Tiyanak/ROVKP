package hr.fer.rovkp.dz3.zad2;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Igor Farszky on 19.5.2017..
 */
public class JokesSimilarity implements ItemSimilarity{

    public Map<Long, TreeSet<JokesSubSimilarity>> jokesSimMap;
    public int k;

    public JokesSimilarity(String path, int k) {
        this.jokesSimMap = new TreeMap<>();
        this.k = k;

        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path)))){

            String record = "";
            JokesSimilarityParser parser = new JokesSimilarityParser();
            while ((record = buffer.readLine()) != null){

                parser = parser.parse(record);
                if (!jokesSimMap.containsKey(parser.getJoke1id().longValue())){
                    JokesSubSimilarity subSim = new JokesSubSimilarity(parser.getJoke2id().longValue(), parser.getSimilarity());
                    TreeSet<JokesSubSimilarity> simTree = new TreeSet<>();
                    simTree.add(subSim);
                    jokesSimMap.put(parser.getJoke1id().longValue(), simTree);
                }else{
                    TreeSet<JokesSubSimilarity> simTree = jokesSimMap.get(parser.getJoke1id().longValue());
                    simTree.add(new JokesSubSimilarity(parser.getJoke2id().longValue(), parser.getSimilarity()));
                    if (simTree.size() > k){
                        simTree.pollLast();
                    }
                    jokesSimMap.replace(parser.getJoke1id().longValue(), simTree);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public double itemSimilarity(long l, long l1) throws TasteException {

        TreeSet<JokesSubSimilarity> simTree = jokesSimMap.get(l);
        if (simTree == null){
            return 0;
        } else {
            for (JokesSubSimilarity subSim : simTree){
                if (subSim.getJokeid() == l1){
                    return subSim.getSimilarity();
                }
            }
            return 0;
        }

    }

    @Override
    public double[] itemSimilarities(long l, long[] longs) throws TasteException {
        double [] result = new double[longs.length];
        for (int i=0; i<longs.length; i++){
            result[i] = itemSimilarity(l, longs[i]);
        }

        return result;
    }

    @Override
    public long[] allSimilarItemIDs(long l) throws TasteException {
        long[] result = new long[k];
        int i = 0;
        for (JokesSubSimilarity subSim : jokesSimMap.get(l)) {
            result[i++] = subSim.getJokeid();
        }
        return result;
    }

    @Override
    public void refresh(Collection<Refreshable> collection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

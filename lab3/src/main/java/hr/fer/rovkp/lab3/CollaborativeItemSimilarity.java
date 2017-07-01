package hr.fer.rovkp.lab3;

import hr.fer.rovkp.dz3.zad2.JokesSubSimilarity;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Igor Farszky on 23.5.2017..
 */
public class CollaborativeItemSimilarity implements ItemSimilarity{

    private double[][] matrix;
    private Map<Integer, Long> seqIdMap;
    private Map<Long, Integer> idSeqMap;
    private final double[] norms;

    public CollaborativeItemSimilarity(DataModel model) throws TasteException {
        int n = model.getNumItems();
        matrix = new double[n][n];
        norms = new double[n];
        seqIdMap = new HashMap<>();
        idSeqMap = new HashMap<>();

        calculateCollaborativeModelMatrix(model);
    }

    private void calculateCollaborativeModelMatrix(DataModel model) throws TasteException {

        int counter = 0;
        LongPrimitiveIterator iterator = model.getUserIDs();
        while (iterator.hasNext()) {
            long userId = iterator.nextLong();

            for (long ratedItemId1 : model.getItemIDsFromUser(userId)) {

                //get correct item1 seq num
                Integer seqId1 = idSeqMap.get(ratedItemId1);
                if (seqId1 == null) {
                    seqId1 = counter++;
                    seqIdMap.put(seqId1, ratedItemId1);
                    idSeqMap.put(ratedItemId1, seqId1);
                }

                norms[seqId1] += Math.pow(model.getPreferenceValue(userId, ratedItemId1), 2);

                for (long ratedItemId2 : model.getItemIDsFromUser(userId)) {

                    //get correct item2 seq num
                    Integer seqId2 = idSeqMap.get(ratedItemId2);
                    if (seqId2 == null) {
                        seqId2 = counter++;
                        seqIdMap.put(seqId2, ratedItemId2);
                        idSeqMap.put(ratedItemId2, seqId2);
                    }

                    matrix[seqId1][seqId2] += model.getPreferenceValue(userId, ratedItemId1)
                            * model.getPreferenceValue(userId, ratedItemId2);
                }
            }
        }

        //get cosine similarity from similarity sums
        for (int seqId1 = 0; seqId1 < matrix.length; seqId1++) {
            for (int seqId2 = 0; seqId2 < matrix.length; seqId2++) {
                if (matrix[seqId1][seqId2] != 0) {
                    matrix[seqId1][seqId2] /= Math.sqrt(norms[seqId1]) * Math.sqrt(norms[seqId2]);
                }
            }
        }
    }

    @Override
    public double itemSimilarity(long l1, long l2) throws TasteException {

        if (!idSeqMap.containsKey(l1) || !idSeqMap.containsKey(l2)){
            return 0.0;
        }

        double similarity = 0.0;

        try{
            similarity = matrix[idSeqMap.get(l1)][idSeqMap.get(l2)];
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return similarity;

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
        long[] result = new long[matrix[idSeqMap.get(l)].length];
        for (int i=0; i<matrix[idSeqMap.get(l)].length; i++) {
            result[i] = idSeqMap.get(l);
        }
        return result;
    }

    @Override
    public void refresh(Collection<Refreshable> collection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double[][] getMatrix() {
        return matrix;
    }

}

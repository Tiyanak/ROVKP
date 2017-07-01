package hr.fer.rovkp.lab3;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Igor Farszky on 24.5.2017..
 */
@SuppressWarnings("Duplicates")
public class ItemSimilarity implements org.apache.mahout.cf.taste.similarity.ItemSimilarity {

    private double[][] itemmatrix;
    private Map<Integer, Long> seqIdMap;
    private Map<Long, Integer> idSeqMap;
    private double[] norms;

    public ItemSimilarity(DataModel model) throws TasteException {
        int n = model.getNumItems();
        itemmatrix = new double[n][n];
        this.norms = new double[n];
        seqIdMap = new HashMap<>();
        idSeqMap = new HashMap<>();

        calculateItemSimilarity(model);
    }

    public void calculateItemSimilarity(DataModel model) throws TasteException {
        int counter = 0;
        LongPrimitiveIterator iterator = model.getUserIDs();
        while (iterator.hasNext()) {
            long itemId = iterator.nextLong();
            Integer seqItemId = idSeqMap.get(itemId);
            if (seqItemId == null) {
                seqItemId = counter++;
                seqIdMap.put(seqItemId, itemId);
                idSeqMap.put(itemId, seqItemId);
            }

            double sum = 0.0;
            for (long ratedItemId1 : model.getItemIDsFromUser(itemId)) {

                //get correct item1 seq num
                Integer seqId1 = idSeqMap.get(ratedItemId1);
                if (seqId1 == null) {
                    seqId1 = counter++;
                    seqIdMap.put(seqId1, ratedItemId1);
                    idSeqMap.put(ratedItemId1, seqId1);
                }

                sum += model.getPreferenceValue(itemId, ratedItemId1);
            }

            for (long ratedItemId1 : model.getItemIDsFromUser(itemId)) {

                //get correct item1 seq num
                Integer seqId1 = idSeqMap.get(ratedItemId1);
                if (seqId1 == null) {
                    seqId1 = counter++;
                    seqIdMap.put(seqId1, ratedItemId1);
                    idSeqMap.put(ratedItemId1, seqId1);
                }

                double prefValuemodel = model.getPreferenceValue(itemId, ratedItemId1) / sum;
                itemmatrix[seqItemId][seqId1] = prefValuemodel;

            }
        }

    }

    public double[][] getItemmatrix() {
        return itemmatrix;
    }

    @Override
    public double itemSimilarity(long l1, long l2) throws TasteException {

        if (!idSeqMap.containsKey(l1) || !idSeqMap.containsKey(l2)) {
            return 0.0;
        }

        double similarity = 0.0;

        try {
            similarity = itemmatrix[idSeqMap.get(l1)][idSeqMap.get(l2)];
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return similarity;

    }

    @Override
    public double[] itemSimilarities(long l, long[] longs) throws TasteException {
        double[] result = new double[longs.length];
        for (int i = 0; i < longs.length; i++) {
            result[i] = itemSimilarity(l, longs[i]);
        }

        return result;
    }

    @Override
    public long[] allSimilarItemIDs(long l) throws TasteException {
        long[] result = new long[itemmatrix[idSeqMap.get(l)].length];
        for (int i = 0; i < itemmatrix[idSeqMap.get(l)].length; i++) {
            result[i] = idSeqMap.get(l);
        }
        return result;
    }

    @Override
    public void refresh(Collection<Refreshable> collection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

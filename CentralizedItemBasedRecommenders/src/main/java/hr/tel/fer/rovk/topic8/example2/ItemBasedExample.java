package hr.tel.fer.rovk.topic8.example2;

import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class ItemBasedExample {

    public static void main(String[] args) throws Exception {

        //inicijaliziraj model učitavanjem podataka o korisnicima učitavanjem iz datoteke
        DataModel model = new FileDataModel(
                new File("/home/kpripuzic/Magnetic/movielens/ml-latest-small/ratings-no_header.csv"));

        //inicijaliziraj sličnost objekata     
        ItemSimilarity similarity = new FileItemSimilarity(
                new File("/home/kpripuzic/Magnetic/movielens/ml-latest-small/item-similarity.csv"));
        //ItemSimilarity similarity = new ShortMovieSimilarity(
        //        "/home/kpripuzic/Magnetic/movielens/ml-latest-small/movies.csv", 3);

        //inicijaliziraj preporučitelja kao preporučitelja temeljenog na sličnosti objekata
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        //izračunaj i ispiši 10 preporuka za korisnika s ID-jem 620 
        List<RecommendedItem> recommendations = recommender.recommend(620, 10);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}

package hr.tel.fer.rovk.topic8.example1;

import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class UserBasedExample {

    public static void main(String[] args) throws Exception {

        //inicijaliziraj model učitavanjem podataka o korisnicima
        DataModel model = new FileDataModel(new File("/home/kpripuzic/Magnetic/movielens/ml-latest-small/ratings-no_header.csv"));
        
        //inicijaliziraj sličnost korisnika koristeći mjeru log-likelihood        
        UserSimilarity similarity = new LogLikelihoodSimilarity(model);
        
        //inicijaliziraj slične korisnike kao 5 najsličnijih po mjeri log-likelihood
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
        
        //inicijaliziraj preporučitelja kao preporučitelja temeljenog na suradnji korisnika
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        
        //izračunaj i ispiši 10 preporuka za korisnika s ID-jem 620 
        List<RecommendedItem> recommendations = recommender.recommend(620, 10);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}

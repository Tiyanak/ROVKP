package hr.tel.fer.rovk.topic8.example3;

import java.io.File;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class EvaluateRecommender1 {

    public static void main(String[] args) throws Exception {

        RandomUtils.useTestSeed();

        //inicijaliziraj model učitavanjem podataka o korisnicima
        DataModel model = new FileDataModel(new File("/home/kpripuzic/Magnetic/movielens/ml-latest-small/ratings-no_header.csv"));
               
        RecommenderBuilder builder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                //inicijaliziraj sličnost korisnika koristeći mjeru log-likelihood        
                UserSimilarity similarity = new LogLikelihoodSimilarity(model);

                //inicijaliziraj slične korisnike kao 5 najsličnijih po mjeri log-likelihood
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);

                //vrati preporučitelja kao preporučitelja temeljenog na suradnji korisnika
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };

        //evaluiraj procjenu 
        RecommenderEvaluator recEvaluator = new RMSRecommenderEvaluator();
        double score = recEvaluator.evaluate(builder, null, model, 0.7, 1.0);
        System.out.println(score);

        //izračunaj odziv i preciznost
        RecommenderIRStatsEvaluator statsEvaluator
                = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = statsEvaluator.evaluate(
                builder, null, model, null, 2,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
                1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
    }
}

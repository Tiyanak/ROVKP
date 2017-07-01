package hr.fer.rovkp.lab3;

import hr.fer.rovkp.dz3.zad2.JokesSimilarity;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Igor Farszky on 24.5.2017..
 */
@SuppressWarnings("Duplicates")
public class Main3 {

    public static void main(String[] args) throws IOException, TasteException {

        System.out.println("HYBRID");
        hybrid(args);
        System.out.println("ITEM BASED");
        itemBased(args);
        System.out.println("USER BASED");
        userBased(args);

    }

    public static void hybrid(String[] args) throws IOException, TasteException {

        DataModel model = new FileDataModel(new File("C:\\Users\\Igor Farszky\\Desktop\\jester_ratings.csv"));
        DataModel simModel = new FileDataModel(new File("C:\\Users\\Igor Farszky\\Desktop\\item_similarity.csv"));

        HybridMatrix hybridMatrix = new HybridMatrix();
        hybridMatrix.calculateHybridSim(new hr.fer.rovkp.lab3.ItemSimilarity(simModel), new CollaborativeItemSimilarity(model));

        DataModel hybridModel = new FileDataModel(new File("C:\\Users\\Igor Farszky\\Desktop\\hybrid.csv"));

        RecommenderBuilder builder = dataModel -> {

            ItemSimilarity similarity = new hr.fer.rovkp.lab3.ItemSimilarity(hybridModel);

            return new GenericItemBasedRecommender(hybridModel, similarity);

        };

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(builder, null, model, 0.7, 1.0);
        System.out.println("RMS Score: " + score);

    }

    public static void itemBased(String[] args) throws IOException, TasteException {

        DataModel model = new FileDataModel(new File("C:\\Users\\Igor Farszky\\Desktop\\jester_ratings.csv"));

        RecommenderBuilder builder = dataModel -> {

            ItemSimilarity similarity = new JokesSimilarity("C:\\Users\\Igor Farszky\\Desktop\\item_similarity.csv", 10);

            return new GenericBooleanPrefItemBasedRecommender(model, similarity);
        };

        List<RecommendedItem> recommendedItems = builder.buildRecommender(model).recommend(220, 10);
        for (RecommendedItem recommendedItem: recommendedItems){
            System.out.println(recommendedItem);
        }

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(builder, null, model, 0.7, 1.0);
        System.out.println(score);

    }

    public static void userBased(String[] args) throws IOException, TasteException {

        //inicijaliziraj model učitavanjem podataka o korisnicima
        DataModel model = new FileDataModel(new File("C:\\Users\\Igor Farszky\\Desktop\\jester_ratings.csv"), ",+");

        RecommenderBuilder builder = dataModel -> {
            //inicijaliziraj sličnost korisnika koristeći mjeru log-likelihood
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            //inicijaliziraj slične korisnike kao 10 najsličnijih po mjeri log-likelihood
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

            //inicijaliziraj preporučitelja kao preporučitelja temeljenog na suradnji korisnika
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            return recommender;
        };

        //izračunaj i ispiši 10 preporuka za korisnika s ID-jem 1
        Recommender userRec = builder.buildRecommender(model);
        List<RecommendedItem> recommendations = userRec.recommend(220, 10);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(builder, null, model, 0.7, 1.0);
        System.out.println(score);

    }

}

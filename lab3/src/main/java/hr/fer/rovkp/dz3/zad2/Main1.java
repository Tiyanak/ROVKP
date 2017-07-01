package hr.fer.rovkp.dz3.zad2;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Igor Farszky on 19.5.2017..
 */
public class Main1 {

    public static void main(String[] args) throws IOException, TasteException {

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
        double score = evaluator.evaluate(builder, null, model, 0.7, 0.3);
        System.out.println(score);

    }

}

package hr.fer.rovkp.dz3.zad2;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Igor Farszky on 19.5.2017..
 */
public class Main2 {

    public static void main(String[] args) throws IOException, TasteException {

        //inicijaliziraj model učitavanjem podataka o korisnicima
        DataModel model = new FileDataModel(new File("C:\\Users\\Igor Farszky\\Desktop\\jester_ratings.csv"));

        RecommenderBuilder builder = dataModel -> {
            //inicijaliziraj sličnost korisnika koristeći mjeru pearson
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

            //inicijaliziraj slične korisnike kao 10 najsličnijih po mjeri pearson
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);

            //inicijaliziraj preporučitelja kao preporučitelja temeljenog na suradnji korisnika
            UserBasedRecommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);

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

        if (args.length == 2) {

            Integer broj_korisnika = Integer.parseInt(args[0]);
            Integer broj_preporuka = Integer.parseInt(args[1]);

            Random r = new Random();

            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Igor Farszky\\Desktop\\recommendations.txt"));
            Recommender recommender = builder.buildRecommender(model);
            LongPrimitiveIterator iterator = model.getUserIDs();
            int i = 0;
            while (iterator.hasNext() && i < broj_korisnika){

                Long userid = iterator.next();

                List<RecommendedItem> recommendations_write = recommender.recommend(userid, broj_preporuka);

                for (RecommendedItem recItem : recommendations_write) {
                    writer.write("" + recItem.getItemID() + "," + recItem.getValue() + "\n");
                }

                i++;

            }

            writer.close();
        }
    }

}

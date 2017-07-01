package hr.fer.rovkp.lab3;

import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Igor Farszky on 24.5.2017..
 */
public class HybridMatrix {

    public HybridMatrix(){
    }

    public void calculateHybridSim(hr.fer.rovkp.lab3.ItemSimilarity sim1, CollaborativeItemSimilarity sim2) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Igor Farszky\\Desktop\\hybrid.csv"));

        double[][] sim1mat = sim1.getItemmatrix();
        double[][] sim2mat = sim2.getMatrix();

        int rows = Math.min(sim1mat.length, sim2mat.length);
        int cols = Math.min(sim1mat[0].length, sim2mat[0].length);

        double[][] result = new double[rows][cols];

        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                result[i][j] = sim1mat[i][j] + sim2mat[i][j];
                writer.write((i+1) + "," + (j+1) + "," + result[i][j] + "\n");
            }
        }

        writer.close();

    }

}

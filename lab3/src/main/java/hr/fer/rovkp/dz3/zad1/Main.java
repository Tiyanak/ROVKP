package hr.fer.rovkp.dz3.zad1;

import hr.fer.rovkp.dz3.JesterParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Igor Farszky on 9.5.2017..
 */
public class Main {

    private static RAMDirectory directory = new RAMDirectory();
    private static Analyzer analyzer = new StandardAnalyzer();

    public static void main(String[] args) throws IOException, ParseException {

        File file = new File("C:\\Users\\Igor Farszky\\Desktop\\jester_items.dat");

        JesterParser jp = new JesterParser(file);

        // 1. ucitaj dataset
        Map<Integer, String> jesterMap = jp.getDataMap();

        // 2. napravi indexe
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        for (Map.Entry<Integer, String> entry : jesterMap.entrySet()) {
            addDocument(entry.getKey(), entry.getValue(), writer);
        }
        writer.commit();
        writer.close();

        // 3. napravi matricu slicnosti + normalizacija
        float[][] simmatrix = new float[jesterMap.size()][jesterMap.size()];

        int i = 0;
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser query = new QueryParser("text", analyzer);
        float max = 0;
        for (Map.Entry<Integer, String> entry : jesterMap.entrySet()) {

            TopDocs docs = searchDocument(entry.getValue(), jesterMap.size(), reader, searcher, query);

            if (docs.getMaxScore() > max) {
                max = docs.getMaxScore();
            }

            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document d = searcher.doc(scoreDoc.doc);
                simmatrix[entry.getKey() - 1][Integer.parseInt(d.get("ID")) - 1] = scoreDoc.score / max;
            }

            i++;

        }

        reader.close();

        // 5. zapisi to sve
        Map<Float, String> simmap = new TreeMap<>();
        BufferedWriter bufWriter = new BufferedWriter(new FileWriter("C:\\Users\\Igor Farszky\\Desktop\\item_similarity.csv"));
        for (int k = 0; k < jesterMap.size(); k++) {
            for (int j = 0; j < jesterMap.size(); j++) {
                if (simmatrix[k][j] > 0) {
                    bufWriter.write((k + 1) + "," + (j + 1) + "," + simmatrix[k][j] + "\n");
                    simmap.put(simmatrix[k][j], k + ":" + j);
                }
            }
        }

        bufWriter.close();

    }

    private static void addDocument(Integer key, String value, IndexWriter writer) throws IOException {

        Document luceneDoc = new Document();

        FieldType idFieldType = new FieldType();
        idFieldType.setStored(true);
        idFieldType.setTokenized(false);
        idFieldType.setIndexOptions(IndexOptions.NONE);
        Field idField = new Field("ID", key.toString(), idFieldType);

        Field textField = new TextField("text", value.toString(), Field.Store.YES);

        luceneDoc.add(idField);
        luceneDoc.add(textField);

        writer.addDocument(luceneDoc);

    }

    private static TopDocs searchDocument(String text, int ntop, IndexReader reader, IndexSearcher searcher, QueryParser query) throws IOException, ParseException {

        Query q = query.parse(QueryParser.escape(text));
        TopDocs topDocs = searcher.search(q, ntop);

        return topDocs;

    }

}

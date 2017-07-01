package hr.fer.rovkp;

import org.apache.hadoop.fs.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Igor Farszky on 28.3.2017..
 */
public class Gutenberg {

    private String booksPath = "/home/rovkp/ifarszky/gutenberg/";
    private String savePathLfs = "/home/rovkp/ifarszky/gutenberg_books.txt";
    private String savePathHdfs = "/user/rovkp/ifarszky/gutenberg_books.txt";

    public Gutenberg() throws FileNotFoundException, UnsupportedEncodingException {
    }

    public Gutenberg(String booksPath) {
        this.booksPath = booksPath;
    }

    public Gutenberg(String booksPath, String savePathLfs, String savePathHdfs) {
        this.booksPath = booksPath;
        this.savePathLfs = savePathLfs;
        this.savePathHdfs = savePathHdfs;
    }

    public void merge(LocalFileSystem lfs, FileSystem hdfs) throws IOException {

        File f = new File(booksPath);
        FilenameFilter textFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        };
        List<File> texts = new ArrayList<>();
        Date start = new Date();

        File[] files = f.listFiles(textFilter);
        for (File file : files){
            System.out.println(file.getCanonicalPath());
            if(file.isDirectory()){
                File[] txtFiles = file.listFiles(textFilter);
                for(File file1 : txtFiles){
                    System.out.println("\t\t" + file1.getCanonicalPath());
                    texts.add(file1);
                }
            }
        }

        BufferedWriter bwhdfs = new BufferedWriter(new OutputStreamWriter(hdfs.create(new Path(savePathHdfs))));
        BufferedWriter bwlfs = new BufferedWriter(new OutputStreamWriter(lfs.create(new Path(savePathLfs))));
        int countLines = 0;
        for(File file : texts){
            BufferedReader br = new BufferedReader(new InputStreamReader(lfs.open(new Path(file.getCanonicalPath()))));

            String line = "";
            while((line = br.readLine()) != null){
                bwhdfs.write(line + "\n");
                bwlfs.write(line + "\n");
                countLines++;
            }

            br.close();

        }

        bwhdfs.close();
        bwlfs.close();

        System.out.println("Broj datoteka: " + texts.size());
        System.out.println("Broj redaka: " + countLines);
        Date end = new Date();
        System.out.println("Vrijeme izvodenja: " + ((double)end.getTime()-start.getTime()) / 1000);
        System.out.println(new File(savePathHdfs).getTotalSpace());

    }

    public String getBooksPath() {
        return booksPath;
    }

    public void setBooksPath(String booksPath) {
        this.booksPath = booksPath;
    }

    public String getSavePathLfs() {
        return savePathLfs;
    }

    public void setSavePathLfs(String savePathLfs) {
        this.savePathLfs = savePathLfs;
    }

    public String getSavePathHdfs() {
        return savePathHdfs;
    }

    public void setSavePathHdfs(String savePathHdfs) {
        this.savePathHdfs = savePathHdfs;
    }
}

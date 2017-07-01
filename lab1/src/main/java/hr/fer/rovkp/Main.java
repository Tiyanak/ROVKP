package hr.fer.rovkp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;

import java.io.EOFException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Igor Farszky on 21.3.2017..
 */
public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        Configuration conf = new Configuration();

        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName() );
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName() );

        FileSystem hdfs = FileSystem.get(conf);
        LocalFileSystem lfs = LocalFileSystem.getLocal(conf);

        System.out.println("LFS home dir: " + lfs.getHomeDirectory().toString());
        System.out.println("HDFS home dir: " + hdfs.getHomeDirectory().toString());

        Path lfsPath = new Path("/home/rovkp/ifarszky");
        Path hdfsPath = new Path("/user/rovkp/ifarszky");

        System.out.println("HDFS FILE: " + hdfs.isFile(hdfsPath));
        System.out.println("HDFS DIRECTORY: " + hdfs.isDirectory(hdfsPath));
        System.out.println("LFS FILE: " + lfs.isFile(lfsPath));
        System.out.println("LFS DIRECTORY: " + lfs.isDirectory(lfsPath));

//      Zadatak 2
        Gutenberg g = new Gutenberg();
        g.merge(lfs, hdfs);

        //Zadatak 3
        Path binPathLfs = new Path("/home/rovkp/ifarszky/ocitanja.bin");
        Path binPathHdfs = new Path("/user/rovkp/ifarszky/ocitanja.bin");

        SequanceFile sfLfs = new SequanceFile(conf, binPathLfs, new IntWritable(0), new FloatWritable(0));
        SequanceFile sfHdfs = new SequanceFile(conf, binPathHdfs, new IntWritable(0), new FloatWritable(0));
        Random r = new Random();

        for(int i=0; i<100000; i++){
            sfLfs.append(new IntWritable(r.nextInt(99)+1), new FloatWritable(r.nextFloat()*100));
            sfHdfs.append(new IntWritable(r.nextInt(99)+1), new FloatWritable(r.nextFloat()*100));
        }

        sfLfs.createReader(conf, binPathLfs);
        sfHdfs.createReader(conf, binPathHdfs);

        IntWritable inter = new IntWritable();
        FloatWritable dob = new FloatWritable();

        Map<IntWritable, FloatWritable> average = new TreeMap<>();
        Map<IntWritable, Integer> count = new TreeMap<>();

        while(inter != null && dob != null){
            try{
                sfLfs.next(inter, dob);
            }catch (EOFException e){
                e.printStackTrace();
                break;
            }
            System.out.println("Key, Value : " + inter.toString() + ", " + dob.toString());

            if(!average.containsKey(inter)){
                average.put(inter, dob);
                count.put(inter, 1);
            }else{
                average.replace(inter, new FloatWritable(average.get(inter).get() + dob.get()));
                count.replace(inter, count.get(inter)+1);
            }
        }

        for(Map.Entry<IntWritable, FloatWritable> entry: average.entrySet()){
            System.out.println("Senzor " + entry.getKey().toString() + ": " + entry.getValue().get()/count.get(entry.getKey()));
        }

    }

}

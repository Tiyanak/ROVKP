package hr.fer.rovkp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;

import java.io.IOException;

/**
 * Created by Igor Farszky on 28.3.2017..
 */
public class SequanceFile {

    private SequenceFile.Writer writer = null;
    private SequenceFile.Reader reader = null;

    public SequanceFile(Configuration conf, Path filePath, Object key, Object value) throws IOException {
        writer = SequenceFile.createWriter(conf,
                SequenceFile.Writer.file(filePath),
                SequenceFile.Writer.keyClass(key.getClass()),
                SequenceFile.Writer.valueClass(value.getClass()));

    }

    public void createWriter(Configuration conf, Path filePath, Object key, Object value) throws IOException {
        writer = SequenceFile.createWriter(conf,
                SequenceFile.Writer.file(filePath),
                SequenceFile.Writer.keyClass(key.getClass()),
                SequenceFile.Writer.valueClass(value.getClass()));
    }

    public void createReader(Configuration conf, Path filePath) throws IOException {
        reader = new SequenceFile.Reader(conf,
                SequenceFile.Reader.file(filePath));
    }

    public void append(Object key, Object value) throws IOException {
        if(writer.getKeyClass().equals(key.getClass()) && writer.getValueClass().equals(value.getClass())) {
            writer.append(key, value);
        }
    }

    public void next(Writable key, Writable value) throws IOException {
        if(writer.getKeyClass().equals(key.getClass()) && writer.getValueClass().equals(value.getClass())) {
            reader.next(key, value);
        }
    }

    public SequenceFile.Writer getWriter() {
        return writer;
    }

    public void setWriter(SequenceFile.Writer writer) {
        this.writer = writer;
    }

    public SequenceFile.Reader getReader() {
        return reader;
    }

    public void setReader(SequenceFile.Reader reader) {
        this.reader = reader;
    }
}

package test.lucene;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.FSDirectory;

import com.hrhih.constant.SpecialConstant;

public class AddRandom {

    public static void main(String[] args) throws IOException,
            InterruptedException {

        // Basic Environment
        FSDirectory dir = FSDirectory.open(new File("D:/MyTest/hrhih/lucene49/"));
        Analyzer analyzer = new StandardAnalyzer(SpecialConstant.LUCENE_VERSION);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(SpecialConstant.LUCENE_VERSION, analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);

        // Real time handler
        TrackingIndexWriter trackingIndexWriter = new TrackingIndexWriter(indexWriter);
        ReferenceManager<IndexSearcher> searcherManager = new SearcherManager(indexWriter, false, null);

        // thread handling
        ControlledRealTimeReopenThread<IndexSearcher> writeControlThread = new ControlledRealTimeReopenThread<IndexSearcher>(
                trackingIndexWriter, searcherManager, 
                1.0, 	// when there is nobody waiting
                0.1		// when there is someone waiting
                );
        
        writeControlThread.setName("Update Reopen Thread");
        writeControlThread.setPriority(Math.min(Thread.currentThread().getPriority() + 2, Thread.MAX_PRIORITY));
        writeControlThread.setDaemon(true);
        writeControlThread.start();

        // start writer and reader, and wait 10 minutes
        WriteDoc wdoc = new WriteDoc(trackingIndexWriter, searcherManager);
        ReadDoc rdoc = new ReadDoc(searcherManager);

        ExecutorService exman = Executors.newFixedThreadPool(5);
        exman.submit(wdoc);
        exman.submit(rdoc);

        Thread.sleep(1L * 60L * 1000L);

        exman.shutdown();
        exman.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Closing...");

        writeControlThread.close();
        searcherManager.close();
        indexWriter.close();
        dir.close();

    }

}
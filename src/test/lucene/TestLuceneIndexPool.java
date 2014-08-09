package test.lucene;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ReferenceManager;

import com.hrhih.index.pool.LuceneIndexPool;

public class TestLuceneIndexPool {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		LuceneIndexPool connPool= new LuceneIndexPool("D:/MyTest/hrhih/lucene49/");
		
		// start writer and reader, and wait 10 minutes
		ReferenceManager<IndexSearcher> referenceManager=connPool.getReferenceManager();
		TrackingIndexWriter trackingIndexWriter = connPool.getTrackingIndexWriter();
        WriteDoc wdoc = new WriteDoc(trackingIndexWriter, referenceManager);
        ReadDoc rdoc = new ReadDoc(referenceManager);

        ExecutorService exman = Executors.newFixedThreadPool(5);
        exman.submit(wdoc);
        exman.submit(rdoc);

        Thread.sleep(1L * 60L * 1000L);

        exman.shutdown();
        exman.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Closing...");
		
        connPool.returnReferenceManager(referenceManager);
        
        connPool.closeReferenceManagerPool();
        connPool.closeTrackingIndexWriter();
	}
}

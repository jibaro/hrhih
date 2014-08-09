package test.lucene;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.hrhih.constant.SpecialConstant;

public class ReadDoc implements Callable<Long> {

    private ReferenceManager<IndexSearcher> searcherManager;
    private final Random rand = new Random(Calendar.getInstance().getTimeInMillis());
    private Analyzer analyzer = new StandardAnalyzer(SpecialConstant.LUCENE_VERSION);
    private QueryParser parser = new QueryParser(SpecialConstant.LUCENE_VERSION, "content",analyzer);

    public ReadDoc(ReferenceManager<IndexSearcher> searchManager) {
        this.searcherManager = searchManager;
    }

	public Long call() throws Exception {
        while (true) {
            try {
                if (Thread.interrupted())
                    break;
                //
                // get index searcher from searcherManager
                //
                IndexSearcher wsrch = searcherManager.acquire();
                //
                // read current status
                //
                System.out.println("------------------ total count: "
                        + wsrch.collectionStatistics("content").docCount());
                Query query = parser.parse("abcde");
                TopDocs topDocs = wsrch.search(query, null, 2000);
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                for (ScoreDoc sd : scoreDocs) {
                    Document wd = wsrch.doc(sd.doc);
                    System.out.println("\tRead: " + wd.get("content"));
                }
                //
                // release index searcher for reopen handling
                //
                searcherManager.release(wsrch);
                //
                // random wait
                //
                if (Thread.interrupted())
                    break;
                Thread.sleep(rand.nextInt(15000));
            } catch (Exception e) {
                break;
            }
        }

        return 0L;

	}

  
}
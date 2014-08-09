package test.lucene;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ReferenceManager;

public class WriteDoc implements Callable<Long> {

    private TrackingIndexWriter trackingIndexWriter;
    private ReferenceManager<IndexSearcher> searcherManager;
    private final Random rand = new Random(Calendar.getInstance()
            .getTimeInMillis());

    public WriteDoc(TrackingIndexWriter trackingIndexWriter,
            ReferenceManager<IndexSearcher> searcherManager) {
        this.trackingIndexWriter = trackingIndexWriter;
        this.searcherManager = searcherManager;
    }

    public Long call() throws Exception {
        Long wcnt = 0L;
        while (true) {
            if (Thread.interrupted())
                break;
            Document doc = new Document();
            Long tweetID = rand.nextLong();
            String userName = anyWord();
            String content = anySentence();
            doc.add(new LongField("tweetID", tweetID, Store.YES));
            doc.add(new StringField("userScreenname", userName, Store.YES));
            doc.add(new TextField("content", content, Store.YES));
            try {
                //
                // add document
                //
                trackingIndexWriter.addDocument(doc);
                System.out.println("\t"+"Write doc: "+content);
                //
                // signal searcher may be reopen to include new document
                //
                searcherManager.maybeRefresh();
                //
                // random wait
                //
                ++wcnt;
                if (Thread.interrupted())
                    break;
                Thread.sleep(rand.nextInt(15000));
            } catch (Exception e) {
                break;
            }
        }

        return wcnt;
    }

    private String anyWord() {
        String r = "";
        String base = "abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int wordLen = 1 + rand.nextInt(9);
        int baseLen = base.length();
        for (int wx = 0; wx < (1 + wordLen); ++wx)
            r += base.charAt(rand.nextInt(baseLen));
        return r;
    }

    private String anySentence() {
        String r = "";
        int wordCount = 1 + rand.nextInt(20);
        for (int wx = 0; wx < wordCount; ++wx)
            r += anyWord() + " ";
        return r + "abcde";
    }

}
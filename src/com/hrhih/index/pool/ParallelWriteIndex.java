/**
 * 
 */
package com.hrhih.index.pool;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TrackingIndexWriter;
import org.slf4j.Logger;

import com.hrhih.utils.MyLog;

/**
 * 并行增加索引
 * @author shuqiang 2013-11-15 上午11:35:29
 */
public class ParallelWriteIndex extends TrackingIndexWriter {
	
	private static Logger log = MyLog.get();
	
	private ExecutorService threadPool;
	private Analyzer defaultAnalyzer;
	
	/**
	 * 新建该对象的时候，初始化一次，之后可以针对该次对象新建的多线程间进行多次调用。
	 * @param indexWrite 写索引对象
	 * @param numThreads 同时执行的线程数
	 * @param maxQueueSize 存放线程的队列大小
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public ParallelWriteIndex(IndexWriter indexWrite,
			int numThreads, int maxQueueSize) throws CorruptIndexException,
			IOException {
		super(indexWrite);
		defaultAnalyzer = indexWrite.getAnalyzer();
		threadPool = new ThreadPoolExecutor(
				// 创建线程池
				numThreads, numThreads, 0, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(maxQueueSize, false),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	/**
	 * 把索引集合分批次生成，如：总数有5000条，1000为一个批次，即List<Document>.size=1000,List<List<Document>>.size=5
	 * @param listdocs
	 */
	public void startOperatIndex(List<List<Document>> listdocs){
		for(int i=0;i<listdocs.size();i++){
			addDocuments(listdocs.get(i)); 
		}
//		MyLog.info(log, "this.batchids==={}", this.batchids);
		listdocs.clear();
	}

	// 让线程池增加一个文档
	public void addDocument(Document doc) {
		threadPool.execute(new DocumentJob(doc, null, defaultAnalyzer));
	}

	// 让线程池增加一个文档
	public void addDocument(Document doc, Analyzer a) {
		threadPool.execute(new DocumentJob(doc, null, a));
	}
	
	// 让线程池增加多个文档
	public void addDocuments(List<Document> docs) {
		threadPool.execute(new DocumentsJob(docs, null, defaultAnalyzer));
	}
	
	// 让线程池增加多个文档
	public void addDocuments(List<Document> docs, Analyzer a) {
		threadPool.execute(new DocumentsJob(docs, null, a));
	}

	// 让线程池更新一个文档
	public void updateDocument(Term term, Document doc) {
		threadPool.execute(new DocumentJob(doc, term, defaultAnalyzer));
	}

	// 让线程池更新一个文档
	public void updateDocument(Term term, Document doc, Analyzer a) {
		threadPool.execute(new DocumentJob(doc, term, a));
	}
	
	// 让线程池更新多个文档
	public void updateDocuments(Term term, List<Document> docs) {
		threadPool.execute(new DocumentsJob(docs, term, defaultAnalyzer));
	}

	// 让线程池更新多个文档
	public void updateDocuments(Term term, List<Document> docs, Analyzer a) {
		threadPool.execute(new DocumentsJob(docs, term, a));
	}

	public void close() throws CorruptIndexException, IOException {
		finish();
	}
	
	private void finish() { // 关闭线程池
		threadPool.shutdown();
		while (true) {
			try {
				if (threadPool.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS)) {
					break;
				}
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(ie);
			}
		}
	}
	
	/**
	 * 操作一个文档
	 * @author shuqiang
	 * 2013-12-18 上午10:47:26
	 */
	private class DocumentJob implements Runnable { // 保留要加入索引的一个文档
		Document doc;
		Analyzer analyzer;
		Term delTerm;

		public DocumentJob(Document doc, Term delTerm, Analyzer analyzer) {
			this.doc = doc;
			this.analyzer = analyzer;
			this.delTerm = delTerm;
		}

		@Override
		public void run() { // 实际增加和更新文档
			try {
				long lt=System.currentTimeMillis();
				if (delTerm != null) {
					ParallelWriteIndex.super.updateDocument(delTerm, doc, analyzer);
				} else {
					ParallelWriteIndex.super.addDocument(doc, analyzer);
				}
				doc=null;
				MyLog.info(log,"DocumentsJobThreadName={},time={}MS", Thread.currentThread().getName(),(System.currentTimeMillis()-lt));
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}
	
	/**
	 * 操作多个文档
	 * @author shuqiang
	 * 2013-12-18 上午10:47:26
	 */
	private class DocumentsJob implements Runnable { // 保留要加入索引的一个文档
		List<Document> docs;
		Analyzer analyzer;
		Term delTerm;

		public DocumentsJob(List<Document> docs, Term delTerm, Analyzer analyzer) {
			this.docs = docs;
			this.analyzer = analyzer;
			this.delTerm = delTerm;
		}

		@Override
		public void run() { // 实际增加和更新文档
			try {
				long lt=System.currentTimeMillis();
				if (delTerm != null) {
					ParallelWriteIndex.super.updateDocuments(delTerm, docs, analyzer);
				} else {
					ParallelWriteIndex.super.addDocuments(docs, analyzer);
				}
				docs.clear();
				docs=null;
				MyLog.info(log,"DocumentsJobThreadName={},time={}MS", Thread.currentThread().getName(),(System.currentTimeMillis()-lt));
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}
}

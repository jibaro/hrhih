package com.hrhih.index.pool;

//http://hi.baidu.com/guowei4634/blog/item/bb16c85c66e90a47faf2c076.html
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;

import com.hrhih.constant.SpecialConstant;
import com.hrhih.utils.MyLog;

/**
 * 单线程写索引、索引查询连接池。
 * @author Yesq
 */
public class LuceneIndexPool20140715 {

	private static Logger logger = MyLog.get();

	private String indexDir; // 索引文件所在目录

	private int initialSize; // 连接池的初始大小

	private int incrementalNum; // 连接池自动增加的大小

	private int maxSize; // 连接池最大的大小

	private String testDir; // 测试连接是否可用的测试索引目录，默认没有测试索引目录

	private TrackingIndexWriter trackingIndexWriter = null;//索引写监控对象

	private Vector<ReferenceManagerState> referenceManagers = null; // 存放连接池中Lucene索引库连接的向量, 初始时为 null

	private Map<ReferenceManager<IndexSearcher>, ControlledRealTimeReopenThread<IndexSearcher>> moniterMap = new HashMap<ReferenceManager<IndexSearcher>, ControlledRealTimeReopenThread<IndexSearcher>>();

	/**
	 * 获取referenceManagers连接池对象
	 * @return
	 */
	public Vector<ReferenceManagerState> getReferenceManagers() {
		return referenceManagers;
	}

	/**
	 * 设置referenceManagers连接池对象
	 * @param referenceManagers
	 */
	public void setReferenceManagers(Vector<ReferenceManagerState> referenceManagers) {
		this.referenceManagers = referenceManagers;
	}

	/**
	 * 构造函数
	 * @param null
	 */
	public LuceneIndexPool20140715() {
		try {
			createTrackingIndexWriter();
			createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 * @param indexDir String 索引库的目录
	 */
	public LuceneIndexPool20140715(String indexDir) {

		this.indexDir = indexDir;
		this.initialSize = 4;
		this.incrementalNum = 2;
		this.maxSize = 8;
		this.testDir = "";

		try {
			createTrackingIndexWriter();
			createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造函数
	 * @param indexDir
	 *            String 索引库的目录
	 * @param initialSize
	 *            int 连接池的初始大小
	 * @param incrementalNum
	 *            int 连接池自动增加的大小
	 * @param maxSize
	 *            int 连接池最大的大小
	 */
	public LuceneIndexPool20140715(String indexDir, int initialSize,int incrementalNum, int maxSize) {
		super();
		this.indexDir = indexDir;
		this.initialSize = initialSize;
		this.incrementalNum = incrementalNum;
		this.maxSize = maxSize;

		try {
			createTrackingIndexWriter();
			createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 * @param indexDir
	 *            String 索引库的目录
	 * @param initialSize
	 *            int 连接池的初始大小
	 * @param incrementalNum
	 *            int 连接池自动增加的大小
	 * @param maxSize
	 *            int 连接池最大的大小
	 * @param testDir
	 *            String 测试索引库的目录
	 */
	public LuceneIndexPool20140715(String indexDir, int initialSize,
			int incrementalNum, int maxSize, String testDir) {
		super();
		this.indexDir = indexDir;
		this.initialSize = initialSize;
		this.incrementalNum = incrementalNum;
		this.maxSize = maxSize;
		this.testDir = testDir;

		try {
			createTrackingIndexWriter();
			createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回Lucene索引库连接池的目录
	 * @return 返回Lucene索引库连接池的目录
	 */
	public String getIndexDir() {
		return indexDir;
	}

	/**
	 * 设置Lucene索引库连接池的目录
	 * @param 用于设置初始连接池中的索引目录
	 */
	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}

	/**
	 * 返回连接池的初始大小
	 * @return 初始连接池中可获得的连接数量
	 */
	public int getInitialSize() {
		return this.initialSize;
	}

	/**
	 * 设置连接池的初始大小
	 * @param 用于设置初始连接池中连接的数量
	 */
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	/**
	 * 返回连接池自动增加的大小 、
	 * @return 连接池自动增加的大小
	 */
	public int getIncrementalNum() {
		return this.incrementalNum;
	}

	/**
	 * 设置连接池自动增加的大小
	 * @param 连接池自动增加的大小
	 */
	public void setIncrementalNum(int incrementalNum) {
		this.incrementalNum = incrementalNum;
	}

	/**
	 * 返回连接池中最大的可用连接数量
	 * @return 连接池中最大的可用连接数量
	 */
	public int getMaxSize() {
		return this.maxSize;
	}

	/**
	 * 设置连接池中最大可用的连接数量
	 * @param 设置连接池中最大可用的连接数量值
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * 获取测试Lucene索引库目录的名字
	 * @return 测试Lucene索引库目录的名字
	 */
	public String getTestDir() {
		return this.testDir;
	}

	/**
	 * 设置测试目录的名字
	 * @param testTable
	 *            String 测试目录的名字
	 */
	public void setTestDir(String testDir) {
		this.testDir = testDir;
	}

	/**
	 * 创建一个Lucene索引库连接池，连接池中的可用连接的数量采用类成员
	 * initialSize 中设置的值
	 */
	public synchronized void createPool() throws Exception {
		// 确保连接池没有创建
		// 如果连接池己经创建了，保存连接的向量 Readers 不会为空
		if (referenceManagers != null) {
			return; // 如果己经创建，则返回
		}
		logger.info("=========>>>>Initial LuceneSegReaderPool  ing......");
		logger.info("=========>>>>PoolPath:  " + indexDir);
		referenceManagers = new Vector<ReferenceManagerState>();
		// 根据 initialReaders 中设置的值，创建连接。
		createReferenceManagers(this.initialSize);
		// System.out.println(" Lucene索引库连接池创建成功！ ");
		logger.info(" Lucene4 ReferenceManagerState referenceManagers Create Success！ ====SegPoolNum: "
				+ referenceManagers.size());
	}

	/**
	 * 创建TrackingIndexWriter对象。
	 * @throws IOException
	 */
	private synchronized void createTrackingIndexWriter() throws IOException {
		if (trackingIndexWriter != null) {
			return;
		}

		// Basic Environment
		
		FSDirectory dir = FSDirectory.open(new File(indexDir));
		Analyzer analyzer = new StandardAnalyzer(SpecialConstant.LUCENE_VERSION);
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				SpecialConstant.LUCENE_VERSION, analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);
		// Real time handler
		trackingIndexWriter = new TrackingIndexWriter(indexWriter);
	}

	/**
	 * 创建由 newSize 指定数目的Lucene索引库连接 , 并把这些连接 放入 referenceManagers 集合中
	 * @param newSize
	 *            要创建的索引库连接的数目
	 */
	private synchronized void createReferenceManagers(int newSize) {
		// 循环创建指定数目的Lucene索引库连接
		for (int x = 0; x < newSize; x++) {
			// 是否连接池中的Lucene索引库连接的数量己经达到最大？最大值由类成员 maxReaders
			// 指出，如果 maxReaders 为 0 或负数，目录示连接数量没有限制。
			// 如果连接数己经达到最大，即退出。
			if (this.maxSize > 0
					&& this.referenceManagers.size() >= this.maxSize) {
				break;
			}
			// add a new PooledReader object to Readers vector
			// 增加一个连接到连接池中（向量 Readers 中）
			// System.out.println("===========>>>> IndexReader  incrementalReaders Now======");
			referenceManagers.addElement(new ReferenceManagerState(
					newReferenceManager()));
			// System.out.println(" 索引库连接己创建 ......");
			logger.info("===========>>>>  Lucene4 ReferenceManagerState Create IndexReader Success!");
		}
	}

	/**
	 * 创建一个新的Lucene索引库连接并返回它
	 * @return 返回一个新创建的Lucene索引库连接
	 * @throws IOException
	 */
	private synchronized ReferenceManager<IndexSearcher> newReferenceManager() {
		ReferenceManager<IndexSearcher> referenceManager = null;
		try {
			referenceManager = new SearcherManager(
					trackingIndexWriter.getIndexWriter(), false, null);
			// thread handling
			ControlledRealTimeReopenThread<IndexSearcher> writeControlThread = new ControlledRealTimeReopenThread<IndexSearcher>(
					trackingIndexWriter, referenceManager, 
					1.0, // when there is nobody waiting
					0.1 // when there is someone waiting
			);

			// writeControlThread.setName("Update Reopen Thread");
			writeControlThread.setPriority(Math.min(Thread.currentThread()
					.getPriority() + 2, Thread.MAX_PRIORITY));
			writeControlThread.setDaemon(true);
			writeControlThread.start();
			moniterMap.put(referenceManager, writeControlThread);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return referenceManager; // 返回创建的新的索引库连接
	}

	/**
	 * 获取索引写对象
	 * @return
	 */
	public TrackingIndexWriter getTrackingIndexWriter() {
		if (trackingIndexWriter == null) {
			try {
				createTrackingIndexWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return trackingIndexWriter;
	}

	/**
	 * 返回一个可用的Lucene索引库连接 ,
	 * 如果当前没有可用的Lucene索引库连接，并且更多的Lucene索引库连接不能创
	 * 建（如连接池大小的限制），此函数等待一会再尝试获取。
	 * @return 返回一个可用的Lucene索引库连接对象
	 */
	public ReferenceManager<IndexSearcher> getReferenceManager() {
		// 确保连接池己被创建
		if (referenceManagers == null) {
			try {
				createPool();
				getReferenceManager();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// return null; // 连接池还没创建，则返回 null
		}
		ReferenceManager<IndexSearcher> referenceManager = getFreeReferenceManager(); // 获得一个可用的Lucene索引库连接
		// 如果目前没有可以使用的连接，即所有的连接都在使用中
		while (referenceManager == null) {
			// 等一会再试 连接池中没有空闲连接
			// System.out.println("Lucene索引库   连接正忙============================");
			wait(250);
			referenceManager = getFreeReferenceManager(); // 重新再试，直到获得可用的连接，如果
			// getFreeReader() 返回的为 null
			// 则目录明创建一批连接后也不可获得可用连接
		}
		return referenceManager;// 返回获得的可用的连接
	}

	/**
	 * 本函数从连接池向量 Readers 中返回一个可用的的Lucene索引库连接，如果
	 * 当前没有可用的Lucene索引库连接，本函数则根据 incrementalNum 设置
	 * 的值创建几个Lucene索引库连接，并放入连接池中。
	 * 如果创建后，所有的连接仍都在使用中，则返回 null
	 * @return 返回一个可用的Lucene索引库连接
	 */
	private ReferenceManager<IndexSearcher> getFreeReferenceManager() {
		// 从连接池中获得一个可用的Lucene索引库连接
		ReferenceManager<IndexSearcher> referenceManager = findFreeReferenceManager();
		if (referenceManager == null) {
			// 如果目前连接池中没有可用的连接
			// 创建一些连接
			createReferenceManagers(incrementalNum);
			// 重新从池中查找是否有可用连接
			// System.out.println(indexReaders.size()+" ====indexReaders.size()======>>>>>>>Readers in the indexReaders pool");
			referenceManager = findFreeReferenceManager();
			if (referenceManager == null) {
				// 如果创建连接后仍获得不到可用的连接，则返回 null
				return null;
			}
		}
		return referenceManager;
	}

	/**
	 * 查找连接池中所有的连接，查找一个可用的Lucene索引库连接，
	 * 如果没有可用的连接，返回 null
	 * @return 返回一个可用的Lucene索引库连接
	 */
	private ReferenceManager<IndexSearcher> findFreeReferenceManager() {
		ReferenceManager<IndexSearcher> referenceManager = null;
		// 获得连接池向量中所有的对象
		// 遍历所有的对象，看是否有可用的连接
		for(ReferenceManagerState pConn:referenceManagers){
			if (!pConn.isBusy()) {
				// 如果此对象不忙，则获得它的Lucene索引库连接并把它设为忙
				referenceManager = pConn.getReferenceManager();
				pConn.setBusy(true);
				// 测试此连接是否可用
				if (!testReferenceManager(referenceManager)) { // 这个语句暂时不会执行,即不用测试.
					// 如果此连接不可再用了，则创建一个新的连接，
					// 并替换此不可用的连接对象，如果创建失败，返回 null
					referenceManager = newReferenceManager();
					pConn.setReferenceManager(referenceManager);
				}
				break; // 己经找到一个可用的连接，退出
			}
		}
		return referenceManager;// 返回找到到的可用连接
	}

	/**
	 * 测试一个连接是否可用，如果不可用，关掉它并返回 false
	 * 否则可用返回 true，这里默认返回true
	 * @param conn
	 *            需要测试的Lucene索引库连接
	 * @return 返回 true 目录示此连接可用， false 目录不可用
	 */
	private boolean testReferenceManager(
			ReferenceManager<IndexSearcher> referenceManager) {
		// 有测试目录的时候使用测试目录测试
		// boolean isIS=indexReader.getIndexReader().isDeleted(1);
		return true;
	}

	/**
	 * 此函数返回一个Lucene索引库连接到连接池中，并把此连接置为空闲。
	 * 所有使用连接池获得的Lucene索引库连接均应在不使用此连接时返回它。
	 * @param 需返回到连接池中的连接对象
	 */
	public void returnReferenceManager(ReferenceManager<IndexSearcher> referenceManager) {
		// 确保连接池存在，如果连接没有创建（不存在），直接返回
		// logger.info("===start===>>>>>>returnIndexReader");
		if (referenceManagers == null) {
			// System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
			return;
		}
		// 遍历连接池中的所有连接，找到这个要返回的连接对象
		for(ReferenceManagerState pConn:referenceManagers){
			// 先找到连接池中的要返回的连接对象
			if (referenceManager == pConn.getReferenceManager()) {
				// 找到了 , 设置此连接为空闲状态
				// logger.info("======>>>>>>returnIndexReader success:  "+indexReader);
				pConn.setBusy(false);
				break;
			}
		}
	}

	/**
	 * 刷新连接池中所有的连接对象
	 */
	public synchronized void refreshReferenceManagers() throws Exception {
		logger.info("======>>>>Refresh IndexReader ");
		// 确保连接池己创新存在
		if (referenceManagers == null) {
			logger.info("=======>>>> the IndexReader Reader Pool exsits!");
			return;
		}
		
		for(ReferenceManagerState pConn:referenceManagers){
			// 如果对象忙则等 5 秒 ,5 秒后直接刷新
			if (pConn.isBusy()) {
				wait(5000); // 等 5 秒
			}
			// 关闭此连接，用一个新的连接代替它。
			closeReferenceManager(pConn.getReferenceManager());
			pConn.setReferenceManager(newReferenceManager());
			pConn.setBusy(false);
		}
	}

	/**
	 * 关闭连接池中所有的连接，并清空连接池。
	 */
	public synchronized void closeReferenceManagerPool() {
		// 确保连接池存在，如果不存在，返回
		if (referenceManagers == null) {
			// System.out.println(" 连接池不存在，无法关闭 !");
			return;
		}
		ReferenceManagerState pConn = null;
		// Enumeration<ReferenceManagerState> enumerate =
		// referenceManagers.elements();
		while (!referenceManagers.isEmpty()) {
			// pConn = (ReferenceManagerState) enumerate.nextElement();
			pConn = (ReferenceManagerState) referenceManagers.firstElement();
			// 如果忙，等 2 秒
			if (pConn.isBusy()) {
				wait(2000); // 等 2 秒
			}
			// 2 秒后直接关闭它
			closeReferenceManager(pConn.getReferenceManager());
			// 从连接池向量中删除它
			referenceManagers.removeElement(pConn);

//			System.out.println("referenceManagers size=="+ referenceManagers.size());
		}
		// 置连接池为空
		referenceManagers = null;
		// closeTrackingIndexWriter(trackingIndexWriter);
	}

	/**
	 * 关闭 trackingIndexWriter
	 * @param trackingIndexWriter
	 */
	public synchronized void closeTrackingIndexWriter() {
		IndexWriter indexWrite = trackingIndexWriter.getIndexWriter();
		if (indexWrite != null) {
			try {
				try {
					indexWrite.commit();
				} catch (IOException e) {
					try {
						indexWrite.rollback();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				Directory dir = indexWrite.getDirectory();
				indexWrite.close();
				if (dir != null) {
					dir.close();
				}
				trackingIndexWriter = null;
				indexWrite = null;
				dir = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void commitTrackingIndexWriter() {
		IndexWriter indexWrite = trackingIndexWriter.getIndexWriter();
		if (indexWrite != null) {
			try {
				indexWrite.commit();
			} catch (IOException e) {
				try {
					indexWrite.rollback();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭连接池中空闲的连接，使连接池中的连接数保持在初始化数。
	 */
	public synchronized void closeFreeReferenceManager() {
		if (referenceManagers == null) {
			// System.out.println(" 连接池不存在，无法关闭 !");
			return;
		}
		ReferenceManagerState pConn = null;
		for(int i=this.initialSize;i<referenceManagers.size();i++){
			pConn = referenceManagers.get(i);
			if (!pConn.isBusy()) {
				closeReferenceManager(pConn.getReferenceManager());
				// 从连接池向量中删除它
				this.referenceManagers.removeElement(pConn);
			}
		}
	}

	/**
	 * 关闭一个Lucene索引库连接
	 * @param 需要关闭的Lucene索引库连接
	 */
	private synchronized void closeReferenceManager(
			ReferenceManager<IndexSearcher> referenceManager) {
		try {
			if (referenceManager != null) {
				ControlledRealTimeReopenThread<IndexSearcher> writeControlThread = moniterMap.remove(referenceManager);
				writeControlThread.close();
				writeControlThread = null;
//				System.out.println("moniterMap size==" + moniterMap.size());

				referenceManager.close();
				referenceManager = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使程序等待给定的毫秒数
	 * @param 给定的毫秒数
	 */
	private void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 内部使用的用于保存连接池中连接对象的类
	 * 此类中有两个成员，一个是Lucene索引库的连接，另一个是指示此连接是否
	 * 正在使用的标志。
	 */
	class ReferenceManagerState {
		private ReferenceManager<IndexSearcher> referenceManager = null;// 索引库连接
		private boolean busy = false; // （闲） 此连接是否正在使用的标志，默认没有正在使用
		
		// 构造函数，根据一个 Reader 构告一个 PooledReader 对象
		public ReferenceManagerState(
				ReferenceManager<IndexSearcher> referenceManager) {
			this.referenceManager = referenceManager;
		}
		
		// 返回此对象中的连接
		public ReferenceManager<IndexSearcher> getReferenceManager() {
			return referenceManager;
		}

		// 设置此对象的，连接
		public void setReferenceManager(
				ReferenceManager<IndexSearcher> referenceManager) {
			this.referenceManager = referenceManager;
		}

		// 获得对象连接是否忙
		public boolean isBusy() {
			return busy;
		}

		// 设置对象的连接正在忙
		public void setBusy(boolean busy) {
			this.busy = busy;
		}
	}

	public static void main(String[] args) throws Exception {
		LuceneIndexPool20140715 connPool = new LuceneIndexPool20140715("D:/MyTest/hrhih/lucene49/");
		ReferenceManager<IndexSearcher> referenceManager0 = connPool.getReferenceManager();
		System.out.println("referenceManager0===========:   "+referenceManager0);
		ReferenceManager<IndexSearcher> referenceManager1 = connPool.getReferenceManager();
		System.out.println("referenceManager1===========:   "+referenceManager1);

		for (int i = 0; i < connPool.referenceManagers.size(); i++) {
			 System.out.println(connPool.getReferenceManagers().get(i).getReferenceManager());
		}

	}
}
